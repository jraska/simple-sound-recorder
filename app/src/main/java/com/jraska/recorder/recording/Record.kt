package com.jraska.recorder.recording

import java.util.*

data class Record(
    val id: UUID,
    val title: String,
    val createdAt: Long = System.currentTimeMillis()
)