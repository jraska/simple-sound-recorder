package com.jraska.recorder.recording

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jraska.recorder.AppSchedulers
import com.jraska.recorder.db.RecorderRepository
import com.jraska.recorder.rx.combineLatest
import com.jraska.recorder.rx.toLiveData
import javax.inject.Inject

class RecorderViewModel @Inject constructor(
    private val recorderRepository: RecorderRepository,
    private val appSchedulers: AppSchedulers,
    private val recordPlayer: RecordsPlayer,
    private val recorder: Recorder
) : ViewModel() {

    var recording: Recorder.OngoingRecording? = null

    fun state(): LiveData<ViewState> {
        return recorderRepository.records()
            .combineLatest(recorder.state()) { records, recordingState -> ViewState(records, recordingState) }
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
            recorderRepository.insert(Record(id, "new record"))
                .subscribeOn(appSchedulers.io)
                .subscribe()
            this.recording = null
        } else {
            this.recording = recorder.startRecording()
        }
    }

    data class ViewState(
        val records: List<Record> = emptyList(),
        val recordingState: RecordingState = RecordingState.DISABLED
    )
}