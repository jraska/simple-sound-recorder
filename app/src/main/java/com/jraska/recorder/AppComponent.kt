package com.jraska.recorder

import androidx.lifecycle.ViewModelProvider
import com.jraska.recorder.recording.RecorderModule
import dagger.Component

@AppSingleton
@Component(
    modules = [RecorderModule::class, AppModule::class, SchedulersModule::class]
)
interface AppComponent {
    fun viewModelFactory(): ViewModelProvider.Factory
}