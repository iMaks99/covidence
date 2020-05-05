package com.sr.covidence.advice

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sr.covidence.models.model.Region
import com.sr.covidence.utils.custom.OnRegionItemClickListener

class RegionsListAdapter(
    private var regions: ArrayList<Region>,
    var itemClickListener: OnRegionItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        RegionViewHolder.create(parent)

    override fun getItemCount(): Int = regions.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RegionViewHolder).bind(regions[position], itemClickListener)
    }
}