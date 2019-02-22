package com.jraska.recorder.recording

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import com.jraska.recorder.AppComponent
import com.jraska.recorder.AppSchedulers
import com.jraska.recorder.DaggerAppComponent
import com.jraska.recorder.SchedulersModule
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test

class RecorderViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun itemsReturnValues() {
        val recorderViewModel = testComponent().viewModelFactory().create(RecorderViewModel::class.java)

        recorderViewModel.items()
            .test()
            .assertHasValue()
    }

    companion object {
        fun testComponent(): AppComponent {
            val schedulers = AppSchedulers(Schedulers.trampoline(), Schedulers.trampoline(), Schedulers.trampoline())

            return DaggerAppComponent.builder()
                .schedulersModule(SchedulersModule(schedulers))
                .build()
        }
    }
}