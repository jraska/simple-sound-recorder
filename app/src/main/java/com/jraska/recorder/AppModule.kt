package com.jraska.recorder

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class AppModule {
    @Provides
    @AppSingleton
    fun viewModelFactoryBinding(factory: ViewModelFactory): ViewModelProvider.Factory {
        return factory
    }
}