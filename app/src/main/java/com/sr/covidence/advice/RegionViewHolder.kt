package com.sr.covidence.advice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sr.covidence.R
import com.sr.covidence.models.model.Region
import com.sr.covidence.utils.custom.OnRegionItemClickListener
import kotlinx.android.synthetic.main.advice_region_item.view.*

class RegionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(region: Region, itemClickListener: OnRegionItemClickListener) {
        itemView.regionTitle.text = region.regionName

        itemView.setOnClickListener {
            itemClickListener.onItemClicked(region)
        }
    }

    companion object {
        fun create(parent: ViewGroup): RegionViewHolder {
            return RegionViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.advice_region_item, parent, false)
            )
        }
    }

}