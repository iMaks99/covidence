package com.sr.covidence.advice

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.sr.covidence.R
import com.sr.covidence.models.model.Company
import kotlinx.android.synthetic.main.advice_company_item.view.*

class CompanyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(company: Company) {
        itemView.adviceCompanyTitle.text = company.title

        val formattedDescription = company.content.split(" ").toTypedArray()

        for (i in formattedDescription.indices)
            when {
                formattedDescription[i].contains("\r\n") -> {
                    val temp = formattedDescription[i].split("\r\n").toTypedArray()
                    for (j in temp.indices)
                        if (temp[j].contains("http"))
                            temp[j] = "<a href='${temp[j]}'>${temp[j]}</a>"

                    formattedDescription[i] = temp.joinToString("<br/>")
                }
                formattedDescription[i].contains("\n") -> {
                    val temp = formattedDescription[i].split("\n").toTypedArray()
                    for (j in temp.indices)
                        if (temp[j].contains("http"))
                            temp[j] = "<a href='${temp[j]}'>${temp[j]}</a>"

                    formattedDescription[i] = temp.joinToString("<br/>")
                }
                formattedDescription[i].contains("http") -> formattedDescription[i] =
                    "<a href='${formattedDescription[i]}'>${formattedDescription[i]}</a>"
            }

        itemView.adviceCompanyContent.movementMethod = LinkMovementMethod.getInstance()

        itemView.adviceCompanyContent.text = HtmlCompat.fromHtml(
            formattedDescription.joinToString(" "),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }

    companion object {
        fun create(parent: ViewGroup): CompanyViewHolder {
            return CompanyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.advice_company_item, parent, false)
            )
        }
    }

}