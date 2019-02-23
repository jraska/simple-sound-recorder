package com.jraska.recorder.recording

import io.reactivex.Completable
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.util.*

const val GPP_EXTENSION = ".3gp"

class StorageRepository(private val soundsDir: File) {
    fun fileForId(id: UUID): File {
        return File(soundsDir, id.toString() + GPP_EXTENSION)
    }

    fun deleteSound(soundId: UUID): Completable {
        return Completable.fromAction {
            val soundFile = fileForId(soundId)
            if (soundFile.exists() && soundFile.delete()) {
                Timber.i("Sound $soundId successfully deleted")
            } else {
                Timber.w("Sound $soundId was not deleted ")
                throw FileNotFoundException("File for $soundId not found")
            }
        }
    }
}