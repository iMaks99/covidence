package com.sr.covidence.profile

import android.annotation.SuppressLint
import android.content.SharedPreferences
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

        progress_bar.visibility = View.GONE
        scroll_view_profile.visibility = View.VISIBLE
    }

}
