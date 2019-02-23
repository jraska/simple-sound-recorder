package com.jraska.recorder.db

import androidx.room.*
import io.reactivex.Observable

@Dao
interface RecordDao {
    @Query("SELECT * FROM record order by created_at")
    fun getAll(): Observable<List<RecordDbo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(record: RecordDbo)

    @Delete
    fun delete(delete: RecordDbo)
}