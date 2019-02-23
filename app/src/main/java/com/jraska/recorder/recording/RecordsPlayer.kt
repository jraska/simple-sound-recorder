package com.jraska.recorder.recording

import android.media.MediaPlayer
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import java.io.File
import java.io.FileNotFoundException
import java.util.*
import javax.inject.Inject

class RecordsPlayer @Inject constructor(
    private val storageRepository: StorageRepository
) {

    fun player(soundId: UUID): Completable {
        val file = storageRepository.fileForId(soundId)

        return MediaPlayerCompletable(file)
    }

    private class MediaPlayerCompletable(val file: File) : Completable() {
        override fun subscribeActual(observer: CompletableObserver) {
            val mediaPlayer = MediaPlayer()

            val disposable = MediaPlayerDisposable(mediaPlayer)
            observer.onSubscribe(disposable)
            if (disposable.isDisposed) {
                return
            }

            if (!file.exists()) {
                observer.onError(FileNotFoundException("File $file does not exist"))
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