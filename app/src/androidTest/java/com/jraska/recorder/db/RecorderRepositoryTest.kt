package com.jraska.recorder.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.jraska.recorder.recording.Record
import io.reactivex.observers.TestObserver
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.util.*

class RecorderRepositoryTest {
    lateinit var recorderRepository: RecorderRepository

    @Before
    fun setUp() {
        val recordsDao =
            Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), RecordDatabase::class.java)
                .build()
                .recordsDao()

        recorderRepository = RecorderRepository(recordsDao)
    }

    @Test
    fun insertUpdateDeleteWorks() {
        val testObserver = recorderRepository.records().test()

        testObserver.awaitCount(1)
        testObserver.assertValue(emptyList())

        val record1 = Record(UUID.randomUUID(), "hello")
        recorderRepository.save(record1).blockingAwait()

        testObserver.awaitCount(2)
        testObserver.assertLastValue(listOf(record1))

        val record2 = Record(UUID.randomUUID(), "world")
        recorderRepository.save(record2).blockingAwait()

        testObserver.awaitCount(3)
        testObserver.assertLastValue(listOf(record1, record2))

        recorderRepository.delete(record1).blockingAwait()

        testObserver.awaitCount(4)
        testObserver.assertLastValue(listOf(record2))
    }

    @Test
    fun updatesValueProperly() {
        val record = Record(UUID.randomUUID(), "ola")

        recorderRepository.save(record).blockingAwait()

        val updatedRecord = record.copy(title = "hello")
        recorderRepository.save(updatedRecord).blockingAwait()

        recorderRepository.records()
            .test()
            .awaitCount(1)
            .assertLastValue(listOf(updatedRecord))
    }

    private fun <T : List<*>> TestObserver<T>.assertLastValue(list: T) {
        assertThat(lastValue())
            .usingFieldByFieldElementComparator()
            .isEqualTo(list)
    }

    private fun <T> TestObserver<T>.lastValue(): T {
        return values()[valueCount() - 1]
    }
}