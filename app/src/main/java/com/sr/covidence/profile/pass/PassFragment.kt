package com.sr.covidence.profile.pass

import android.R.attr.phoneNumber
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sr.covidence.R
import com.sr.covidence.utils.showFragment
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_pass.*


class PassFragment : Fragment() {

    private lateinit var pref: SharedPreferences

    private var countExit = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = context!!.getSharedPreferences("sharedPreferences", AppCompatActivity.MODE_PRIVATE)

        val v = layoutInflater.inflate(R.layout.custom_toolbar, null)
        pass_toolbar.addView(v)
        v.profile_title.text = "Информация о пропусках"
        v.profile_back_btn.setOnClickListener {

            pref.edit().putString("countExit", countExit.toString()).apply()

            fragmentManager!!.popBackStack()
        }
        v.profile_settings_image.setOnClickListener {
            showFragment(TemplateFragment(), fragmentManager!!)
        }

        countExit = pref.getString("countExit", "0")!!.toInt()
        count_exit.setText(pref.getString("countExit", "0"), TextView.BufferType.EDITABLE)

        buttonMinus.setOnClickListener {
            if (countExit > 0) {
                countExit--
                count_exit.setText(countExit.toString(), TextView.BufferType.EDITABLE)
            }
        }

        bottomAdd.setOnClickListener {
            countExit++
            count_exit.setText(countExit.toString(), TextView.BufferType.EDITABLE)
        }

        get_pass_btn.isEnabled = pref.getBoolean("templateDone", false)
        get_pass_btn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.data = Uri.parse("smsto:7377") // This ensures only SMS apps respond

            intent.putExtra("sms_body", pref.getString("templateForPass", ""))
            startActivity(intent)
        }
    }

}
