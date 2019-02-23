package com.jraska.recorder.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record")
data class RecordDbo(
    @PrimaryKey var uid: String,
    @ColumnInfo(name = "name") var name: String = ""
)