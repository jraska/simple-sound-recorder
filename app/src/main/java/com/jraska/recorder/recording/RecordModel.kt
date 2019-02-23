package com.jraska.recorder.recording

import android.view.View
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyModel
import com.jraska.recorder.R
import kotlinx.android.synthetic.main.recorded_item.view.*

class RecordModel(
    val record: Record,
    private val clickListener: (Record) -> Unit,
    private val deleteListener: (Record) -> Unit,
    private val recordNewTitleListener: (Record, String) -> Unit
) : EpoxyModel<View>() {
    override fun getDefaultLayout(): Int {
        return R.layout.recorded_item
    }

    override fun bind(view: View) {
        view.record_play.text = record.title
        view.record_play.setOnClickListener { clickListener(record) }
        view.record_delete.setOnClickListener { deleteListener(record) }

        view.record_edit_container.isVisible = false
        view.record_edit.setOnClickListener { view.record_edit_container.isVisible = true }

        view.record_edit_save.setOnClickListener {
            val newName = view.record_name_edit.text.toString()
            recordNewTitleListener(record, newName)
        }
    }
}