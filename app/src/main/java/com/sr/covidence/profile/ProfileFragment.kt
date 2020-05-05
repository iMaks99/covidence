package com.sr.covidence.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.sr.covidence.R
import com.sr.covidence.login.AuthorizationFragment
import com.sr.covidence.models.dto.GetUserResponse
import com.sr.covidence.models.dto.User
import com.sr.covidence.network.NetworkService
import com.sr.covidence.profile.mask.MaskFragment
import com.sr.covidence.profile.pass.PassFragment
import com.sr.covidence.utils.showFragment
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ProfileFragment : Fragment() {

    private lateinit var pref: SharedPreferences
    var retrofitClientInstance: NetworkService = NetworkService.instance!!

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = context!!.getSharedPreferences("sharedPreferences", AppCompatActivity.MODE_PRIVATE)

        val v = layoutInflater.inflate(R.layout.custom_toolbar, null)
        profile_toolbar.addView(v)
        v.profile_title.text = "Профиль"
        v.profile_back_btn.visibility = View.GONE
        v.profile_settings_image.setOnClickListener {
            showFragment(AdditionalnfoProfileFragment(), fragmentManager!!)
        }

        exit_button.setOnClickListener {

            pref.edit().clear().apply()

            pref.edit()
                .putBoolean("isLogin", false)
                .apply()

            showFragment(AuthorizationFragment(), fragmentManager!!)

        }

        constraint_for_mask.setOnClickListener {
            showFragment(MaskFragment(), fragmentManager!!)
        }

        constraint_for_pass.setOnClickListener {
            showFragment(PassFragment(), fragmentManager!!)
        }

        constraint_for_online_doctor.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data =
                Uri.parse("https://onlinedoctor.ru/doctors/")
            startActivity(openURL)
        }

        constraint_for_aid.setOnClickListener {
            callAid()
        }

        getProfile()

    }

    private fun getProfile() {

        retrofitClientInstance.profileEndpoint!!.getUser(
            accessToken = pref.getString("accessToken", "")!!,
            secretAccessToken = pref.getString("secretAccessToken", "")!!,
            apiType = "mobile"
        ).enqueue(object : Callback<GetUserResponse> {

            override fun onResponse(
                call: Call<GetUserResponse>,
                response: Response<GetUserResponse>
            ) {
                if (response.isSuccessful) {

                    user = response.body()!!.data

                    buildProfile()

                }
            }

            override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }

    @SuppressLint("SetTextI18n")
    private fun buildProfile() {

        pref.edit().putString("rating", "5").apply()

        profile_fio.text =
            user.lastname + " " + user.firstname

        profile_count_mask.text = pref.getString("countMask", "0")
        if (profile_count_mask.text.toString().toInt() > 1) {
            profile_count_mask.setTextColor(context!!.resources.getColor(R.color.profile_rating_good_text_color))
        } else {
            profile_count_mask.setTextColor(context!!.resources.getColor(R.color.profile_rating_bad_text_color))
        }

        profile_count_exit.text = pref.getString("countExit", "0")
        if (profile_count_exit.text.toString().toInt() <= 3) {
            profile_count_exit.setTextColor(context!!.resources.getColor(R.color.profile_rating_good_text_color))
        } else {
            profile_count_exit.setTextColor(context!!.resources.getColor(R.color.profile_rating_bad_text_color))
        }

        rating_for_user.text = pref.getString("rating", "0")
        if (rating_for_user.text.toString().toInt() > 2) {
            rating_for_user.setTextColor(context!!.resources.getColor(R.color.profile_rating_good_text_color))
        } else {
            rating_for_user.setTextColor(context!!.resources.getColor(R.color.profile_rating_bad_text_color))
        }

        switch_for_autosend.isChecked = pref.getBoolean("autoSend", false)
        number_phone_friend.setText(
            pref.getString("phoneAutoSend", "")!!,
            TextView.BufferType.EDITABLE
        )

        switch_for_autosend.setOnCheckedChangeListener { _, _ ->
            if (switch_for_autosend.isChecked)
                pref.edit().putBoolean("autoSend", true).apply()
            else
                pref.edit().putBoolean("autoSend", false).apply()
        }

        number_phone_friend.addTextChangedListener {
            pref.edit().putString("phoneAutoSend", number_phone_friend.text.toString()).apply()
        }

        progress_bar.visibility = View.GONE
        scroll_view_profile.visibility = View.VISIBLE
    }

    @SuppressLint("MissingPermission")
    private fun callAid() {
        val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "112"))
        startActivity(callIntent)

        if (switch_for_autosend.isChecked) {
            try {
                val sms = SmsManager.getDefault()

                val locationManager =
                    context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager?

                val locationGPS: Location? =
                    locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                val addresses: List<Address>
                val geocoder: Geocoder = Geocoder(context, Locale.getDefault())

                addresses = geocoder.getFromLocation(
                    locationGPS!!.latitude,
                    locationGPS.longitude,
                    1
                )

                val message = "Привет. Я вызвал скорую на этот адрес: " + addresses[0]
                sms.sendTextMessage(
                    number_phone_friend.text.toString(),
                    null,
                    message,
                    null,
                    null
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
