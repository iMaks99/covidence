package com.sr.covidence.browse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sr.covidence.R
import com.sr.covidence.browse.entertainment.EntertainmentFragment
import com.sr.covidence.browse.map.MapFragment
import com.sr.covidence.browse.news.NewsFragment
import com.sr.covidence.utils.showFragment
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_browse.*

class BrowseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browse, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val v = layoutInflater.inflate(R.layout.custom_toolbar, null)
        browse_toolbar.addView(v)
        v.profile_title.text = "Обзор"
        v.profile_back_btn.visibility = View.GONE
        v.profile_settings_image.visibility = View.GONE

        constraint_for_map.setOnClickListener {
            showFragment(MapFragment(), fragmentManager!!)
        }

        constraint_for_news.setOnClickListener {
            showFragment(NewsFragment(), fragmentManager!!)
        }

        constraint_for_entertainment.setOnClickListener {
            showFragment(EntertainmentFragment(), fragmentManager!!)
        }


    }

}
