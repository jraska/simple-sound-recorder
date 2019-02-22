package com.jraska.recorder.recording

import androidx.lifecycle.ViewModel
import com.jraska.recorder.AppSingleton
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
class RecorderModule {

    @Provides
    @AppSingleton
    fun searchRepository(): RecorderRepository {
        return RecorderRepository()
    }

    @Provides
    @IntoMap
    @ClassKey(RecorderViewModel::class)
    fun provideViewModel(viewModel: RecorderViewModel): ViewModel {
        return viewModel
    }
}