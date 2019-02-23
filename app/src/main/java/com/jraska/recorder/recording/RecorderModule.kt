package com.jraska.recorder.recording

import androidx.lifecycle.ViewModel
import com.jraska.recorder.AppSingleton
import com.jraska.recorder.db.RecordDao
import com.jraska.recorder.db.RecorderRepository
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
class RecorderModule {

    @Provides
    @AppSingleton
    fun repository(dao: RecordDao): RecorderRepository {
        return RecorderRepository(dao)
    }

    @Provides
    @IntoMap
    @ClassKey(RecorderViewModel::class)
    fun provideViewModel(viewModel: RecorderViewModel): ViewModel {
        return viewModel
    }
}