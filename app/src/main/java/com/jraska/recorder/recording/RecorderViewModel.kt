package com.jraska.recorder.recording

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jraska.recorder.AppSchedulers
import com.jraska.recorder.db.RecorderRepository
import com.jraska.recorder.rx.combineLatest
import com.jraska.recorder.rx.toLiveData
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class RecorderViewModel @Inject constructor(
    private val recorderRepository: RecorderRepository,
    private val appSchedulers: AppSchedulers,
    private val recordPlayer: RecordsPlayer,
    private val recorder: Recorder
) : ViewModel() {

    var recording: OngoingRecording? = null
    private val recordingState = BehaviorSubject.createDefault(RecordingState.READY_TO_RECORD)

    fun state(): LiveData<ViewState> {
        return recorderRepository.records()
            .combineLatest(recordingState) { records, recordingState -> ViewState(records, recordingState) }
            .distinctUntilChanged()
            .subscribeOn(appSchedulers.io)
            .observeOn(appSchedulers.mainThread)
            .toLiveData()
    }

    fun onItemClicked(record: Record) {
        recordPlayer.play(record.id)
    }

    fun onRecordToggleClicked() {
        val recording = this.recording
        if (recording != null) {
            val id = recording.stopRecording()
            recordingState.onNext(RecordingState.READY_TO_RECORD)
        } else {
            this.recording = recorder.startRecording()
            recordingState.onNext(RecordingState.RECORDING)
        }
    }

    data class ViewState(
        val records: List<Record> = emptyList(),
        val recordingState: RecordingState = RecordingState.DISABLED
    )

    enum class RecordingState(val recording: Boolean, val uiEnabled: Boolean = true) {
        DISABLED(false, false),
        RECORDING(true, true),
        READY_TO_RECORD(false, true),
    }
}