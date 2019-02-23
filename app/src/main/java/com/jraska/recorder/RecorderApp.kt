package com.jraska.recorder

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.jraska.recorder.db.DaggerDatabaseComponent
import com.jraska.recorder.db.DatabaseModule
import timber.log.Timber

open class RecorderApp : Application(), HasViewModelFactory {

    private val appComponent: AppComponent by lazy { createAppComponent() }

    override fun factory(): ViewModelProvider.Factory {
        return appComponent.viewModelFactory()
    }

    override fun onCreate() {
        super.onCreate()

//        setupCrashReporting()
        setupLogging()
    }

    private fun setupLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
//            Timber.plant(SomeReportingTree())
        }
    }

    private fun createAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(filesDir))
            .databaseComponent(
                DaggerDatabaseComponent.builder()
                    .databaseModule(DatabaseModule(this))
                    .build()
            )
            .build()
    }
}