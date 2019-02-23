package com.jraska.recorder

import androidx.lifecycle.ViewModelProvider
import com.jraska.recorder.recording.StorageRepository
import dagger.Module
import dagger.Provides
import java.io.File

@Module
class AppModule(
    private val soundDir: File
) {
    @Provides
    @AppSingleton
    fun viewModelFactoryBinding(factory: ViewModelFactory): ViewModelProvider.Factory {
        return factory
    }

    @Provides
    @AppSingleton
    fun storageRepository(): StorageRepository {
        return StorageRepository(soundDir)
    }
}