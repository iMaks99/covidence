package com.sr.covidence.login

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar

import com.sr.covidence.R
import com.sr.covidence.profile.ProfileFragment
import com.sr.covidence.utils.showFragment
import kotlinx.android.synthetic.main.fragment_verification.*

class VerificationFragment : Fragment() {

    private lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = context!!.getSharedPreferences("sharedPreferences", AppCompatActivity.MODE_PRIVATE)

        code_input.addTextChangedListener {

            verification_button.isEnabled = code_input.text.toString().length == 6
        }

        verification_button.setOnClickListener {
            if (code_input.text.toString().length == 6) {
                pref.edit()
                    .putBoolean("isLogin", true)
                    .apply()

                showFragment(ProfileFragment(), fragmentManager!!)

            } else {
                Snackbar.make(
                    this.view!!,
                    "Введен неправильный код",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

}
