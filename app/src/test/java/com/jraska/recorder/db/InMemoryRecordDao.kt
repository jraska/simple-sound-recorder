package com.jraska.recorder.db

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class InMemoryRecordDao(
    private val list: MutableList<RecordDbo> = mutableListOf(),
    private val subject: BehaviorSubject<List<RecordDbo>> = BehaviorSubject.createDefault(listOf())
) : RecordDao {
    override fun getAll(): Observable<List<RecordDbo>> {
        return subject
    }

    override fun insertOrUpdate(record: RecordDbo) {
        val existing = list.find { it.uid == record.uid }
        if (existing != null) {
            val indexOf = list.indexOf(existing)
            list[indexOf] = record
        } else {
            list.add(record)
        }

        subject.onNext(list)
    }

    override fun delete(delete: RecordDbo) {
        list.removeIf { it.uid == delete.uid }
        subject.onNext(list)
    }
}