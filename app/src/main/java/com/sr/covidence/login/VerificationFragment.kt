package com.sr.covidence.login

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar

import com.sr.covidence.R
import com.sr.covidence.models.dto.SignUpResponse
import com.sr.covidence.network.NetworkService
import com.sr.covidence.profile.ProfileFragment
import com.sr.covidence.utils.showFragment
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_verification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerificationFragment : Fragment() {

    private lateinit var pref: SharedPreferences

    var retrofitClientInstance: NetworkService = NetworkService.instance!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val v = layoutInflater.inflate(R.layout.custom_toolbar, null)
        verification_toolbar.addView(v)
        v.profile_title.text = "Подтверждение аккаунта"
        v.profile_back_btn.visibility = View.VISIBLE
        v.profile_settings_image.visibility = View.GONE

        v.profile_back_btn.setOnClickListener {
            fragmentManager!!.popBackStack()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                fragmentManager!!.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        pref = context!!.getSharedPreferences("sharedPreferences", AppCompatActivity.MODE_PRIVATE)

        code_input.addTextChangedListener {

            verification_button.isEnabled = code_input.text.toString().length == 6
        }

        verification_button.setOnClickListener {
            if (code_input.text.toString().length == 6) {
                sendCode(code_input.text.toString())
            }
        }
    }

    private fun sendCode(code: String) {
        retrofitClientInstance.registrationEndpoint!!.sendCode(
            email = pref.getString("email", "")!!,
            regKey = code
        ).enqueue(object : Callback<SignUpResponse> {

            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                if (response.isSuccessful) {

                    pref.edit()
                        .putBoolean("isLogin", true)
                        .apply()

                    showFragment(ProfileFragment(), fragmentManager!!)

                } else {
                    Snackbar.make(
                        verification_content,
                        "Введен неправильный код",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}
