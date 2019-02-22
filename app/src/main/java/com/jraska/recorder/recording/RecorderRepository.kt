package com.jraska.recorder.recording

import io.reactivex.Observable
import io.reactivex.Single

class RecorderRepository constructor(

) {
    fun records(): Observable<List<Record>> {
        return Observable.just(listOf(Record("Hello world"), Record("Greetings")))
    }
}