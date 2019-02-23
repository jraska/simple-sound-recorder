package com.jraska.recorder.recording

import io.reactivex.Observable
import java.util.*

class RecorderRepository constructor(

) {
    fun records(): Observable<List<Record>> {
        return Observable.just(listOf(Record(UUID.randomUUID(), "Hello world"), Record(UUID.randomUUID(), "Greetings")))
    }
}