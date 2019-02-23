package com.jraska.recorder.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule(private val context: Context) {
    @Provides
    fun recordDao(): RecordDao {
        return Room.databaseBuilder(context, RecordDatabase::class.java, "records")
            .build()
            .recordsDao()
    }
}
