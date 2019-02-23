package com.jraska.recorder.db

import com.jraska.recorder.recording.Record
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*

class RecorderRepository constructor(
    private val recordDao: RecordDao
) {
    fun records(): Observable<List<Record>> {
        return recordDao.getAll()
            .map { records -> records.map { convert(it) } }
    }

    fun insert(record: Record): Completable {
        return Completable.fromAction { recordDao.insert(convert(record)) }
    }

    fun delete(record: Record): Completable {
        return Completable.fromAction { recordDao.delete(convert(record)) }
    }

    private fun convert(dbo: RecordDbo): Record {
        return Record(UUID.fromString(dbo.uid), dbo.name)
    }

    private fun convert(record: Record): RecordDbo {
        return RecordDbo(record.id.toString(), record.title)
    }
}