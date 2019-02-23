package com.jraska.recorder.recording

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import com.jraska.recorder.*
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test
import java.io.File

class RecorderViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun itemsReturnValues() {
        val recorderViewModel = testComponent().viewModelFactory().create(RecorderViewModel::class.java)

        recorderViewModel.state()
            .test()
            .assertHasValue()
    }

    companion object {
        fun testComponent(): AppComponent {
            val schedulers = AppSchedulers(Schedulers.trampoline(), Schedulers.trampoline(), Schedulers.trampoline())

            return DaggerAppComponent.builder()
                .schedulersModule(SchedulersModule(schedulers))
                .appModule(AppModule(File("")))
                .build()
        }
    }
}