package com.sr.covidence.journal

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

import com.sr.covidence.R
import com.sr.covidence.models.dto.*
import com.sr.covidence.network.NetworkService
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.custom_toolbar.view.profile_back_btn
import kotlinx.android.synthetic.main.custom_toolbar.view.profile_title
import kotlinx.android.synthetic.main.custom_toolbar_for_send_note.view.*
import kotlinx.android.synthetic.main.fragment_authorization.*
import kotlinx.android.synthetic.main.fragment_send_note.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendNoteFragment : Fragment() {

    private lateinit var pref: SharedPreferences

    var retrofitClientInstance: NetworkService = NetworkService.instance!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = context!!.getSharedPreferences("sharedPreferences", AppCompatActivity.MODE_PRIVATE)

        val v = layoutInflater.inflate(R.layout.custom_toolbar_for_send_note, null)
        toolbar_send_note.addView(v)
        v.profile_title.text = "Запись в дневнике"
        v.profile_back_btn.setOnClickListener {
            fragmentManager!!.popBackStack()
        }
        v.profile_send_image.setOnClickListener {

            if (user_message_input.text.isNotEmpty()) {
                sendNote(user_message_input.text.toString())
            } else {
                Snackbar.make(
                    view,
                    "Нельзя отправить пустую запись в дневник",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun sendNote(message: String) {
        retrofitClientInstance.journalEndpoint!!.sendNote(
            accessToken = pref.getString("accessToken", "")!!,
            secretAccessToken = pref.getString("secretAccessToken", "")!!,
            apiType = "mobile",
            text = message,
            covidLikelihood = pref.getString("covidLikelihood", "")!!
        ).enqueue(object : Callback<SendNoteDto> {

            override fun onResponse(
                call: Call<SendNoteDto>,
                response: Response<SendNoteDto>
            ) {
                if (response.isSuccessful) {

                    val newNote = Note(
                        response.body()!!.data.dateCreation,
                        response.body()!!.data.dateCreation,
                        response.body()!!.data.id,
                        message
                    )

                    JournalFragment.listOfNote.add(0, newNote)
                    JournalFragment.newsRecyclerView.notifyItemInserted(0)
                    fragmentManager!!.popBackStack()
                }
            }

            override fun onFailure(call: Call<SendNoteDto>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
