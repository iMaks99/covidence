package com.sr.covidence.browse.news

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sr.covidence.R
import com.sr.covidence.models.dto.News
import com.sr.covidence.utils.GlideApp
import kotlinx.android.synthetic.main.news_list_item.view.*
import kotlinx.android.synthetic.main.news_photo_item.view.*
import kotlinx.android.synthetic.main.news_text_item.view.*

class NewsRecyclerViewAdapter(
    var items: ArrayList<News>,
    var callback: Callback
) : RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(
        holder: NewsHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    inner class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(item: News) {

            itemView.title_news.text = item.title

            itemView.source_news.text = item.tags[0]

            for (i in item.html!!.iterator()) {
                if (i.html != null) {
                    val text =
                        LayoutInflater.from(itemView.context).inflate(R.layout.news_text_item, null)

                    if (itemView.content_news_linear.childCount < item.html!!.size) {
                        text.content_news.text = Html.fromHtml(i.html)
                        itemView.content_news_linear.addView(text)
                    }
                } else if (i.img != null) {
                    val photo =
                        LayoutInflater.from(itemView.context)
                            .inflate(R.layout.news_photo_item, null)

                    if (itemView.content_news_linear.childCount < item.html!!.size) {
                        GlideApp.with(itemView.context).load(i.img)
                            .into(photo.photo_content)
                        itemView.content_news_linear.addView(photo)
                    }
                }
            }

        }
    }

    interface Callback {
        fun onItemClicked(item: News)
    }
}