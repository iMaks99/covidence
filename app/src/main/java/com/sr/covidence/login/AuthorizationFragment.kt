package com.sr.covidence.login

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

import com.sr.covidence.R
import com.sr.covidence.models.dto.SignInResponse
import com.sr.covidence.models.dto.SignUpResponse
import com.sr.covidence.network.NetworkService
import com.sr.covidence.profile.ProfileFragment
import com.sr.covidence.utils.showFragment
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_authorization.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorizationFragment : Fragment() {

    private lateinit var pref: SharedPreferences

    var retrofitClientInstance: NetworkService = NetworkService.instance!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_authorization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = context!!.getSharedPreferences("sharedPreferences", AppCompatActivity.MODE_PRIVATE)

        val v = layoutInflater.inflate(R.layout.custom_toolbar, null)
        authorization_toolbar.addView(v)
        v.profile_title.text = "Авторизация"
        v.profile_back_btn.visibility = View.GONE
        v.profile_settings_image.visibility = View.GONE

        registration_button.setOnClickListener {
            showFragment(RegistrationFragment(), fragmentManager!!)
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                fragmentManager!!.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        log_in_button.setOnClickListener {

            if (login_or_email_input.text.toString().isNotEmpty() && password_input.text.toString()
                    .isNotEmpty()
            ) {
                signIn(login_or_email_input.text.toString(), password_input.text.toString())

            } else {
                Snackbar.make(
                    this.view!!,
                    "Заполните все поля",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

    }

    private fun signIn(user: String, pass: String) {

        retrofitClientInstance.registrationEndpoint!!.signIn(
            user = user,
            pass = pass
        ).enqueue(object : Callback<SignInResponse> {

            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                if (response.isSuccessful) {

                    pref.edit()
                        .putString("accessToken", response.body()!!.accessToken)
                        .apply()

                    pref.edit()
                        .putString("secretAccessToken", response.body()!!.secretAccessToken)
                        .apply()

                    showFragment(ProfileFragment(), fragmentManager!!)
                } else {
                    Snackbar.make(
                        authorization_content,
                        "Неправильный логин или пароль",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }

}
