package com.jraska.recorder.recording

import com.jraska.recorder.db.RecorderRepository
import io.reactivex.Completable
import javax.inject.Inject

class DeleteRecordUseCase @Inject constructor(
    private val recorderRepository: RecorderRepository,
    private val storageRepository: StorageRepository
) {
    fun execute(record: Record): Completable {
        return recorderRepository.delete(record)
            .andThen(storageRepository.deleteSound(record.id))
    }
}