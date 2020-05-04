package com.sr.covidence.journal

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sr.covidence.R
import com.sr.covidence.models.dto.Note
import kotlinx.android.synthetic.main.journal_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class JournalRecyclerViewAdapter (
    var items: ArrayList<Note>,
    var callback: Callback
) : RecyclerView.Adapter<JournalRecyclerViewAdapter.JournalHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = JournalHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.journal_list_item, parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(
        holder: JournalHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    inner class JournalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(item: Note) {

            itemView.content_note.text = item.recordData

            itemView.pub_date_note.text = DateUtils.getRelativeTimeSpanString(
                item.dataCreation * 1000,
                System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS
            )

//            itemView.pub_date_note.text = SimpleDateFormat(
//                "dd.MM.yyyy",
//                Locale.ENGLISH
//            ).format(item.dataCreation * 1000)

        }
    }

    interface Callback {
        fun onItemClicked(item: Note)
    }
}