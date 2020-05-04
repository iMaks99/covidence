package com.sr.covidence.login

import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

import com.sr.covidence.R
import com.sr.covidence.models.dto.SignUpResponse
import com.sr.covidence.network.NetworkService
import com.sr.covidence.utils.showFragment
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.view.*
import kotlinx.android.synthetic.main.fragment_registration.view.agreement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationFragment : Fragment(), View.OnClickListener {

    private lateinit var pref: SharedPreferences

    var retrofitClientInstance: NetworkService = NetworkService.instance!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = context!!.getSharedPreferences("sharedPreferences", AppCompatActivity.MODE_PRIVATE)

        val v = layoutInflater.inflate(R.layout.custom_toolbar, null)
        registration_toolbar.addView(v)
        v.profile_title.text = "Регистрация"
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

        setRegisterButton(view)
        setListeners(view)
        setCheckBox(view)
    }

    private fun setRegisterButton(view: View) {
        view.done_sign_up_button.isEnabled = view.agreement.isChecked
    }

    private fun setCheckBox(view: View) {
        view.agreement_text.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun setListeners(view: View) {
        view.agreement.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.id == view.agreement.id)
                view.done_sign_up_button.isEnabled = b
        }
        view.done_sign_up_button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.done_sign_up_button -> {
                val userAgreement = agreement.isChecked
                val firstname = firstname_input.text.toString()
                val lastname = lastname_input.text.toString()
                val middlename = patronymic_input.text.toString()
                val login = login_input.text.toString()
                val password = password_input.text.toString()
                val phone = number_phone_input.text.toString()
                val email = email_input.text.toString()

                if (firstname.isNotEmpty() && lastname.isNotEmpty() && middlename.isNotEmpty() &&
                    login.isNotEmpty() && password.isNotEmpty() &&
                    phone.isNotEmpty() && email.isNotEmpty()
                ) {

                    signUp(
                        userAgreement, login, password, email,
                        firstname, lastname, middlename, phone
                    )


                } else {

                    Snackbar.make(
                        this.view!!,
                        "Должны быть заполнены все поля, кроме отчества",
                        Snackbar.LENGTH_LONG
                    ).show()
                }

            }
        }
    }

    private fun signUp(
        userAgreement: Boolean,
        user: String,
        hashPass: String,
        email: String,
        firstname: String,
        lastname: String,
        middlename: String,
        phone: String
    ) {
        retrofitClientInstance.registrationEndpoint!!.signUp(
            userAgreement = userAgreement,
            user = user,
            pass = hashPass,
            email = email,
            firstname = firstname,
            lastname = lastname,
            middlename = middlename,
            phone = phone
        ).enqueue(object : Callback<SignUpResponse> {

            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                if (response.isSuccessful) {

                    pref.edit()
                        .putString("email", email)
                        .apply()

                    showFragment(VerificationFragment(), fragmentManager!!)

                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
