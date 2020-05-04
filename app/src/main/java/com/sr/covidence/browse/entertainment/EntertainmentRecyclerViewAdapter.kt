package com.sr.covidence.browse.entertainment

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sr.covidence.R
import com.sr.covidence.models.dto.EntertainmentCardDto
import kotlinx.android.synthetic.main.entertainment_list_item.view.*

class EntertainmentRecyclerViewAdapter(
    var items: ArrayList<EntertainmentCardDto>,
    var callback: Callback
) : RecyclerView.Adapter<EntertainmentRecyclerViewAdapter.EntertainmentHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EntertainmentHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.entertainment_list_item, parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(
        holder: EntertainmentHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    inner class EntertainmentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(item: EntertainmentCardDto) {

            itemView.entertainment_card_title.text = item.title

            itemView.entertainment_card_subtitle.text = item.subtitle

            itemView.entertainment_card_background.setImageDrawable(item.image)

        }
    }

    interface Callback {
        fun onItemClicked(item: EntertainmentCardDto)
    }
}