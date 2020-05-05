package com.sr.covidence.advice

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sr.covidence.models.model.Company
import com.sr.covidence.models.model.Region
import com.sr.covidence.utils.custom.OnRegionItemClickListener

class CompanyListAdapter(
    private var companies: ArrayList<Company>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CompanyViewHolder.create(parent)

    override fun getItemCount(): Int = companies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CompanyViewHolder).bind(companies[position])
    }
}