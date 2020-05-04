package com.sr.covidence.browse.entertainment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.sr.covidence.R
import com.sr.covidence.models.dto.EntertainmentCardDto
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_entertainment.*

class EntertainmentFragment : Fragment() {

    private val listOfEntertainment = ArrayList<EntertainmentCardDto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entertainment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val v = layoutInflater.inflate(R.layout.custom_toolbar, null)
        browse_entertainment_toolbar.addView(v)
        v.profile_title.text = "Досуг на карантине"
        v.profile_back_btn.setOnClickListener {
            fragmentManager!!.popBackStack()
        }
        v.profile_settings_image.visibility = View.GONE

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                fragmentManager!!.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        buildCardData()

        buildRecycler()
    }

    private fun buildCardData() {

        listOfEntertainment.add(
            EntertainmentCardDto(
                "Какой сериал посмотреть?",
                "Netflix поможет вам с выбором",
                context!!.getDrawable(R.drawable.netflix)!!,
                "https://netflix-free.life/serialss/"
            )
        )

        listOfEntertainment.add(
            EntertainmentCardDto(
                "Какой фильм посмотреть на самоизоляции?",
                "В топе кинопоиска можно найти ответ и на этот вопрос",
                context!!.getDrawable(R.drawable.film)!!,
                "https://www.kinopoisk.ru/lists/top250/?is-redirected=1"
            )
        )

        listOfEntertainment.add(
            EntertainmentCardDto(
                "Чем бы перекусить?",
                "Когда разобрался с духовной пищей можно подумать и о физиологии",
                context!!.getDrawable(R.drawable.food)!!,
                "https://eda.yandex"
            )
        )

        listOfEntertainment.add(
            EntertainmentCardDto(
                "Доставка продуктов",
                "Для вашей безопасности",
                context!!.getDrawable(R.drawable.deliverly)!!,
                "https://www.utkonos.ru"
            )
        )
    }

    private fun buildRecycler() {

        val entertainmentRecyclerView =
            EntertainmentRecyclerViewAdapter(listOfEntertainment,
                object : EntertainmentRecyclerViewAdapter.Callback {

                    override fun onItemClicked(item: EntertainmentCardDto) {
                        val openURL = Intent(Intent.ACTION_VIEW)
                        openURL.data =
                            Uri.parse(item.link)
                        context!!.startActivity(openURL)
                    }
                })

        entertainment_list.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        entertainment_list.adapter = entertainmentRecyclerView

    }
}
