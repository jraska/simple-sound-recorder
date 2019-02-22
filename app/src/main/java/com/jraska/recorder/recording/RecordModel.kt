package com.jraska.recorder.recording

import android.widget.TextView
import com.airbnb.epoxy.EpoxyModel
import com.jraska.recorder.R

class RecordModel(val record: Record, val clickListener: (Record) -> Unit) : EpoxyModel<TextView>() {
    override fun getDefaultLayout(): Int {
        return R.layout.recorded_item
    }

    override fun bind(view: TextView) {
        view.text = record.title
        view.setOnClickListener { clickListener(record) }
    }
}