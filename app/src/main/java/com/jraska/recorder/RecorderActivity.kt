package com.jraska.recorder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.SimpleEpoxyAdapter
import com.jraska.recorder.recording.Record
import com.jraska.recorder.recording.RecordModel
import com.jraska.recorder.recording.RecorderViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class RecorderActivity : AppCompatActivity() {

    private val viewModel by lazy { viewModel(RecorderViewModel::class.java) }
    private val adapter = SimpleEpoxyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        main_fab.setOnClickListener { viewModel.onRecordToggleClicked() }

        viewModel.state().observe(this, Observer { onState(it) })
    }

    private fun onState(state: RecorderViewModel.ViewState) {
        val records = state.records

        bindRecords(records)

        main_fab.isEnabled = state.recordingState.uiEnabled
        if (state.recordingState.recording) {
            setFabIcon(android.R.drawable.ic_dialog_alert)
        } else {
            setFabIcon(android.R.drawable.ic_dialog_dialer)
        }
    }

    private fun bindRecords(records: List<Record>) {
        if (!recordsChanged(records)) {
            return
        }

        val models = records.map { RecordModel(it, viewModel::onItemClicked, viewModel::onDeleteClicked) }
        adapter.removeAllModels()
        adapter.addModels(models)
    }

    private fun recordsChanged(records: List<Record>): Boolean {
        val previousRecords = adapter.models.map { (it as RecordModel).record }
        return previousRecords != records
    }

    private fun setFabIcon(resource: Int) {
        main_fab.setImageDrawable(ContextCompat.getDrawable(this, resource))
    }
}
