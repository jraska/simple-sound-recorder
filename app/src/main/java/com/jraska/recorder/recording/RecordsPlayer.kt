package com.jraska.recorder.recording

import android.media.MediaPlayer
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
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

    private class MediaPlayerCompletable(val file: File) : Completable() {
        override fun subscribeActual(observer: CompletableObserver) {
            val mediaPlayer = MediaPlayer()

            val disposable = MediaPlayerDisposable(mediaPlayer)
            observer.onSubscribe(disposable)
            if (disposable.isDisposed) {
                return
            }

            try {
                mediaPlayer.setDataSource(file.absolutePath)
                mediaPlayer.prepare()
                mediaPlayer.start()
                disposable.playerInitialized
            } catch (ex: Throwable) {
                observer.onError(ex)
                return
            }

            mediaPlayer.setOnCompletionListener {
                mediaPlayer.release()
                observer.onComplete()
            }
        }
    }

    private class MediaPlayerDisposable constructor(
        private val mediaPlayer: MediaPlayer
    ) : Disposable {
        @Volatile
        private var disposed: Boolean = false
        var playerInitialized: Boolean = false

        override fun dispose() {
            if (disposed) {
                return
            }
            disposed = true

            if (playerInitialized) {
                mediaPlayer.pause()
                mediaPlayer.stop()
            }
        }

        override fun isDisposed(): Boolean {
            return disposed
        }
    }
}