package com.jraska.recorder.recording

import android.media.MediaPlayer
import timber.log.Timber
import java.io.File
import java.util.*
import javax.inject.Inject

class RecordsPlayer @Inject constructor(
    private val storageRepository: StorageRepository
) {

    fun play(soundId: UUID) {
        val file = storageRepository.fileForId(soundId)
        if (file.exists()) {
            play(file)
        } else {
            Timber.e("Sound %s file not found.", soundId)
        }
    }

    private fun play(file: File) {
        val mediaPlayer = MediaPlayer()

        mediaPlayer.setDataSource(file.absolutePath)
        mediaPlayer.prepare()
        mediaPlayer.start()

        mediaPlayer.setOnCompletionListener { mediaPlayer.release() }
    }
}