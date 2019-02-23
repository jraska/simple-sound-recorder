package com.jraska.recorder.recording

import android.media.MediaRecorder
import java.util.*
import javax.inject.Inject

class Recorder @Inject constructor(
    private val storageRepository: StorageRepository
) {
    fun startRecording(): OngoingRecording {
        val fileId = UUID.randomUUID()
        val outputFile = storageRepository.fileForId(fileId)

        val recorder = MediaRecorder()
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder.setOutputFile(outputFile.absolutePath)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        recorder.prepare()
        recorder.start()

        return OngoingRecording(recorder, fileId)
    }
}

class OngoingRecording(
    private val recorder: MediaRecorder,
    private val id: UUID
) {
    fun stopRecording(): UUID {
        recorder.stop()
        recorder.release()
        return id
    }
}