package com.jraska.recorder.rx

import io.reactivex.Scheduler

class AppSchedulers(
    val mainThread: Scheduler,
    val computation: Scheduler,
    val io: Scheduler
)