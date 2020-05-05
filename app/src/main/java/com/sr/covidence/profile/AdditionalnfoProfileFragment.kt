package com.sr.covidence.profile

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.sr.covidence.R
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_additionalnfo_profile.*


class AdditionalnfoProfileFragment : Fragment() {

    private lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_additionalnfo_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = context!!.getSharedPreferences("sharedPreferences", AppCompatActivity.MODE_PRIVATE)

        val v = layoutInflater.inflate(R.layout.custom_toolbar, null)
        additional_info_toolbar.addView(v)
        v.profile_title.text = "Дополнительная информация"
        v.profile_back_btn.setOnClickListener {
            fragmentManager!!.popBackStack()
        }
        v.profile_settings_image.setImageDrawable(context!!.getDrawable(R.drawable.ic_done))
        v.profile_settings_image.setOnClickListener {

            pref.edit().putString("emailForSend", email_doctor_input.text.toString()).apply()

            pref.edit().putString("oms", oms_input.text.toString()).apply()

            fragmentManager!!.popBackStack()
        }

        oms_input.setText(pref.getString("oms", "")!!, TextView.BufferType.EDITABLE)
        email_doctor_input.setText(
            pref.getString("emailForSend", "")!!,
            TextView.BufferType.EDITABLE
        )


    }

}
