package com.sr.covidence.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.sr.covidence.R
import com.sr.covidence.journal.JournalFragment
import com.sr.covidence.login.AuthorizationFragment
import com.sr.covidence.models.dto.GetUserResponse
import com.sr.covidence.models.dto.JournalResponse
import com.sr.covidence.models.dto.User
import com.sr.covidence.network.NetworkService
import com.sr.covidence.utils.showFragment
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
        v.profile_settings_image.visibility = View.GONE

        exit_button.setOnClickListener {

            pref.edit()
                .putBoolean("isLogin", false)
                .apply()

            showFragment(AuthorizationFragment(), fragmentManager!!)

        }

        getProfile()

        constraint_for_mask.setOnClickListener {

        }

        constraint_for_pass.setOnClickListener {

        }

        constraint_for_online_doctor.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data =
                Uri.parse("https://onlinedoctor.ru/doctors/")
            startActivity(openURL)
        }

        constraint_for_aid.setOnClickListener {

        }
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

        profile_fio.text =
            user.lastname + " " + user.firstname

        profile_count_mask.text = pref.getString("countMask", "0")
        if (profile_count_mask.text.toString().toInt() > 1) {
            profile_count_mask.setTextColor(context!!.resources.getColor(R.color.profile_rating_good_text_color))
        } else {
            profile_count_mask.setTextColor(context!!.resources.getColor(R.color.profile_rating_bad_text_color))
        }

        profile_count_exit.text = pref.getString("countExit", "0")
        if (profile_count_exit.text.toString().toInt() > 1) {
            profile_count_exit.setTextColor(context!!.resources.getColor(R.color.profile_rating_good_text_color))
        } else {
            profile_count_exit.setTextColor(context!!.resources.getColor(R.color.profile_rating_bad_text_color))
        }

        rating_for_user.text = pref.getString("rating", "0")
        if (rating_for_user.text.toString().toInt() > 1) {
            rating_for_user.setTextColor(context!!.resources.getColor(R.color.profile_rating_good_text_color))
        } else {
            rating_for_user.setTextColor(context!!.resources.getColor(R.color.profile_rating_bad_text_color))
        }

        progress_bar.visibility = View.GONE
        scroll_view_profile.visibility = View.VISIBLE
    }

}
