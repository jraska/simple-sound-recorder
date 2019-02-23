package com.jraska.recorder.recording

import android.media.MediaRecorder
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import javax.inject.Inject

class Recorder @Inject constructor(
    private val storageRepository: StorageRepository
) {
    private val subject: BehaviorSubject<RecordingState> = BehaviorSubject.createDefault(RecordingState.READY_TO_RECORD)

    fun state(): Observable<RecordingState> {
        return subject
    }

    fun startRecording(): OngoingRecording {
        val fileId = UUID.randomUUID()
        val outputFile = storageRepository.fileForId(fileId)

        subject.onNext(RecordingState.RECORDING)

        val recorder = MediaRecorder()
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder.setOutputFile(outputFile.absolutePath)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        recorder.prepare()
        recorder.start()

        return OngoingRecording(this, recorder, fileId)
    }

    private fun stopRecording() {
        subject.onNext(RecordingState.READY_TO_RECORD)
    }

    class OngoingRecording(
        private val recorder: Recorder,
        private val mediaRecorder: MediaRecorder,
        private val id: UUID
    ) {
        fun stopRecording(): UUID {
            mediaRecorder.stop()
            mediaRecorder.release()

            recorder.stopRecording()
            return id
        }
    }
}


enum class RecordingState(val recording: Boolean, val uiEnabled: Boolean = true) {
    DISABLED(false, false),
    RECORDING(true, true),
    READY_TO_RECORD(false, true),
}