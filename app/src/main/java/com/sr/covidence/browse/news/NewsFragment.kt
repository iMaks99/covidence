package com.sr.covidence.browse.news

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.sr.covidence.R
import com.sr.covidence.models.dto.News
import com.sr.covidence.models.dto.NewsResponse
import com.sr.covidence.network.NetworkService
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {

    private lateinit var pref: SharedPreferences

    var retrofitClientInstance: NetworkService = NetworkService.instance!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = context!!.getSharedPreferences("sharedPreferences", AppCompatActivity.MODE_PRIVATE)

        val v = layoutInflater.inflate(R.layout.custom_toolbar, null)
        browse_news_toolbar.addView(v)
        v.profile_title.text = "Новости от официальных источников"
        v.profile_back_btn.setOnClickListener {
            fragmentManager!!.popBackStack()
        }
        v.profile_settings_image.visibility = View.GONE

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                fragmentManager!!.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        getNews()
    }

    private fun buildRecycler(listOfNews: ArrayList<News>) {

        val newsRecyclerView =
            NewsRecyclerViewAdapter(listOfNews,
                object : NewsRecyclerViewAdapter.Callback {

                    override fun onItemClicked(item: News) {

                    }
                })

        news_list.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        news_list.adapter = newsRecyclerView

        progress_bar.visibility = View.GONE
        news_list.visibility = View.VISIBLE

    }

    private fun getNews() {

        retrofitClientInstance.browseEndpoint!!.getAllNews(

        ).enqueue(object : Callback<NewsResponse> {

            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                if (response.isSuccessful) {

                    buildRecycler(response.body()!!.data.posts)
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }

}
