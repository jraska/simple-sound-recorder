package com.jraska.recorder

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

open class RecorderApp : Application() {

    private val appComponent: AppComponent by lazy { createAppComponent() }

    fun viewModelFactory(): ViewModelProvider.Factory {
        return appComponent.viewModelFactory()
    }

    override fun onCreate() {
        super.onCreate()

//        setupCrashReporting()
        setupLogging()
        setupThreeTen()
    }

    private fun setupLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
//            Timber.plant(SomeReportingTree())
        }
    }

    private fun setupThreeTen() {
        AndroidThreeTen.init(this)
    }

    private fun createAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .build()
    }
}

fun <T : ViewModel> FragmentActivity.viewModel(modelClass: Class<T>): T {
    val factory = (application as RecorderApp).viewModelFactory()
    return ViewModelProviders.of(this, factory).get(modelClass)
}