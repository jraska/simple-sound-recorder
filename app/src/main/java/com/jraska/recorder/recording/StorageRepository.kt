package com.jraska.recorder.recording

import timber.log.Timber
import java.io.File
import java.util.*

const val GPP_EXTENSION = ".3gp"

class StorageRepository(val soundsDir: File) {
    fun fileForId(id: UUID): File {
        return File(soundsDir, id.toString() + GPP_EXTENSION)
    }

    fun deleteSound(soundId: UUID) {
        val soundFile = fileForId(soundId)
        if (soundFile.exists() && soundFile.delete()) {
            Timber.i("Sound $soundId successfully deleted")
        } else {
            Timber.w("Sound $soundId was not deleted ")
        }
    }

//    private var recorder: MediaRecorder? = null

//    fun startRecording() {
//        if (isRecording) {
//            return
//        }
//
//        _currentFileId = UUID.randomUUID()
//        val outputFile = getFileForId(_currentFileId)
//
//        recorder = MediaRecorder()
//        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
//        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
//        recorder.setOutputFile(outputFile.absolutePath)
//        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
//
//        recorder.prepare()
//
//        recorder.start()
//    }

//    /**
//     * Stops current recording and gets id of existing record if any
//     *
//     * @return Id of saved file, null if there is no record.
//     */
//    fun stopRecording(): UUID? {
//        if (!isRecording) {
//            return null
//        }
//
//        recorder.stop()
//        recorder.release()
//        recorder = null
//
//        val fileId = _currentFileId
//        _currentFileId = null
//
//        return fileId
//    }
}