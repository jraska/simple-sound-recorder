package com.jraska.recorder.rx

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.BiFunction

fun <T1, T2, R> Observable<T1>.combineLatest(
    source2: ObservableSource<T2>,
    combiner: (T1, T2) -> R
): Observable<R> {
    return Observable.combineLatest(this, source2, BiFunction<T1, T2, R> { t1, t2 -> combiner(t1, t2) })
}

fun <T> Observable<T>.toLiveData(): LiveData<T> {
    return toFlowable(BackpressureStrategy.MISSING).toLiveData()
}