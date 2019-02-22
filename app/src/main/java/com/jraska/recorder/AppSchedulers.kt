package com.jraska.recorder

import io.reactivex.Scheduler

class AppSchedulers(
    val mainThread: Scheduler,
    val computation: Scheduler,
    val io: Scheduler
)