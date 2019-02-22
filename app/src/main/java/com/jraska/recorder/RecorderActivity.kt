package com.jraska.recorder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.SimpleEpoxyAdapter
import com.google.android.material.snackbar.Snackbar
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

        main_fab.setOnClickListener { view ->
            Snackbar.make(view, "TODO: Recording", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        viewModel.items().observe(this, Observer { onItems(it) })
    }

    private fun onItems(records: List<Record>) {
        val models = records.map { RecordModel(it, viewModel::onItemClicked) }
        adapter.removeAllModels()
        adapter.addModels(models)
    }
}
