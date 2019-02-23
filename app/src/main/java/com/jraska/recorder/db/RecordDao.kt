package com.jraska.recorder.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface RecordDao {
    @Query("SELECT * FROM record")
    fun getAll(): Observable<List<RecordDbo>>

    @Insert
    fun insert(record: RecordDbo)

    @Delete
    fun delete(delete: RecordDbo)
}