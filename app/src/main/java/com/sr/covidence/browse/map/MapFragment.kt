package com.sr.covidence.browse.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback

import com.sr.covidence.R
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_browse.*
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val v = layoutInflater.inflate(R.layout.custom_toolbar, null)
        browse_map_toolbar.addView(v)
        v.profile_title.text = "Карта распространения"
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


        val item =
            "<iframe src=\"https://coronavirus-monitor.ru/map-moscow\" frameBorder=\"0\" height=\"400\" width=\"900\" style=\"max-width: 100%;\"></iframe>"

        map_web_view.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean {
                return false
            }
        }

        map_web_view.settings.userAgentString = "Android"
        map_web_view.settings.domStorageEnabled = true
        map_web_view.settings.javaScriptEnabled = true


        map_web_view.loadDataWithBaseURL(
            null,
            item,
            "text/html",
            "UTF-16",
            null
        )
    }

}
