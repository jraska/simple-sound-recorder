package com.jraska.recorder.recording

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.jraska.recorder.AppSchedulers
import io.reactivex.BackpressureStrategy
import javax.inject.Inject

class RecorderViewModel @Inject constructor(
    private val recorderRepository: RecorderRepository,
    private val appSchedulers: AppSchedulers
) : ViewModel() {

    fun items(): LiveData<List<Record>> {
        return recorderRepository.records()
            .subscribeOn(appSchedulers.io)
            .observeOn(appSchedulers.mainThread)
            .toFlowable(BackpressureStrategy.MISSING)
            .toLiveData()
    }

    fun onItemClicked(record: Record) {

    }
}