package com.sr.covidence.journal

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.sr.covidence.R
import com.sr.covidence.models.dto.JournalResponse
import com.sr.covidence.models.dto.Note
import com.sr.covidence.network.NetworkService
import com.sr.covidence.utils.showFragment
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_journal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JournalFragment : Fragment() {

    var retrofitClientInstance: NetworkService = NetworkService.instance!!

    private lateinit var pref: SharedPreferences

    companion object{
        var listOfNote = ArrayList<Note>()
        lateinit var newsRecyclerView : JournalRecyclerViewAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_journal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = context!!.getSharedPreferences("sharedPreferences", AppCompatActivity.MODE_PRIVATE)

        val v = layoutInflater.inflate(R.layout.custom_toolbar, null)
        journal_toolbar.addView(v)
        v.profile_title.text = "Дневник"
        v.profile_back_btn.visibility = View.GONE
        v.profile_settings_image.visibility = View.GONE

        getAllNote()

        send_note_btn.setOnClickListener {
            showFragment(SendNoteFragment(), fragmentManager!!)
        }
    }

    private fun buildRecycler() {

        newsRecyclerView =
            JournalRecyclerViewAdapter(listOfNote,
                object : JournalRecyclerViewAdapter.Callback {

                    override fun onItemClicked(item: Note) {

                    }
                })

        note_list.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        note_list.adapter = newsRecyclerView

        progress_bar.visibility = View.GONE
        note_list.visibility = View.VISIBLE
        send_note_btn.visibility = View.VISIBLE

    }

    private fun getAllNote() {
        retrofitClientInstance.journalEndpoint!!.getAllNote(
            accessToken = pref.getString("accessToken","")!!,
            secretAccessToken = pref.getString("secretAccessToken", "")!!,
            apiType = "mobile"
        ).enqueue(object : Callback<JournalResponse> {

            override fun onResponse(
                call: Call<JournalResponse>,
                response: Response<JournalResponse>
            ) {
                if (response.isSuccessful) {
                    listOfNote = response.body()!!.data
                    buildRecycler()
                }
            }

            override fun onFailure(call: Call<JournalResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}

