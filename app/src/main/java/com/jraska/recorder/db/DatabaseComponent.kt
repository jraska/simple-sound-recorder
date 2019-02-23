package com.jraska.recorder.db

import dagger.Component

@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {
    fun recordDao(): RecordDao
}