package com.jraska.recorder.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecordDbo::class], version = 1)
abstract class RecordDatabase : RoomDatabase() {
    abstract fun recordsDao(): RecordDao
}