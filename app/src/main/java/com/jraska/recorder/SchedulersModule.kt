package com.jraska.recorder

import com.jraska.recorder.rx.AppSchedulers
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class SchedulersModule(
    private val appSchedulers: AppSchedulers = AppSchedulers(
        AndroidSchedulers.mainThread(),
        Schedulers.computation(),
        Schedulers.io()
    )
) {
    @Provides
    fun appSchedulers(): AppSchedulers {
        return appSchedulers
    }
}