package com.sr.covidence.profile.mask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sr.covidence.R
import com.sr.covidence.network.NetworkService

class MaskFragment : Fragment() {

    var retrofitClientInstance: NetworkService = NetworkService.instance!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mask, container, false)
    }

}
