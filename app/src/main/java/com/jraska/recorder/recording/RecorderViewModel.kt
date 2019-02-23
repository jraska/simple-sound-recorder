package com.jraska.recorder.recording

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jraska.recorder.AppSchedulers
import com.jraska.recorder.db.RecorderRepository
import com.jraska.recorder.rx.combineLatest
import com.jraska.recorder.rx.toLiveData
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class RecorderViewModel @Inject constructor(
    private val recorderRepository: RecorderRepository,
    private val appSchedulers: AppSchedulers,
    private val recordPlayer: RecordsPlayer,
    private val recorder: Recorder
) : ViewModel() {

    private var recording: Recorder.OngoingRecording? = null
    private val disposables = CompositeDisposable()

    fun state(): LiveData<ViewState> {
        return recorderRepository.records()
            .combineLatest(recorder.state()) { records, recordingState -> ViewState(records, recordingState) }
            .distinctUntilChanged()
            .subscribeOn(appSchedulers.io)
            .observeOn(appSchedulers.mainThread)
            .toLiveData()
    }

    fun onItemClicked(record: Record) {
        val disposable = recordPlayer.player(record.id)
            .subscribeOn(appSchedulers.io)
            .subscribe({ Timber.d("Played $record") }, { Timber.e(it) })

        disposables.add(disposable)
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

    override fun onCleared() {
        disposables.clear()
    }

    data class ViewState(
        val records: List<Record> = emptyList(),
        val recordingState: RecordingState = RecordingState.DISABLED
    )
}