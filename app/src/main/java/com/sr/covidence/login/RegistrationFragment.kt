package com.sr.covidence.login

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar

import com.sr.covidence.R
import com.sr.covidence.utils.showFragment
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.view.*
import kotlinx.android.synthetic.main.fragment_registration.view.agreement

class RegistrationFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

//                    signUp(
//                        userAgreement, user, hashPass, email,
//                        firstname, lastname, middlename, company, companyPosition
//                    )
                    showFragment(VerificationFragment(), fragmentManager!!)

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
}
