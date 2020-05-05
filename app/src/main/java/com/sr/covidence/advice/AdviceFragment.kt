package com.sr.covidence.advice

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sr.covidence.MainActivity

import com.sr.covidence.R
import com.sr.covidence.models.model.Region
import com.sr.covidence.models.dto.AdviceDto
import com.sr.covidence.models.model.Company
import com.sr.covidence.utils.custom.OnRegionItemClickListener
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_advice.*


class AdviceFragment : Fragment(), OnRegionItemClickListener {

    private lateinit var advice: AdviceDto
    private lateinit var adviceViewModel: AdviceViewModel
    private lateinit var companyListAdapter: CompanyListAdapter
    private var companies = ArrayList<Company>()
    private var isCompanies = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_advice, container, false)
    }

    override fun onItemClicked(region: Region) {
        adviceItemTitle.text = region.regionName
        adviceItemContent.movementMethod = LinkMovementMethod.getInstance()
        adviceItemContent.text = Html.fromHtml(region.restriction)
        (context as MainActivity).hideRegionsBottomSheet()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val v = layoutInflater.inflate(R.layout.custom_toolbar, null)
        advice_toolbar.addView(v)
        v.profile_title.text = "Советы"
        v.profile_back_btn.visibility = View.GONE
        v.profile_settings_image.visibility = View.GONE

        adviceViewModel = ViewModelProvider(this).get(AdviceViewModel::class.java)
        adviceViewModel.getGeneralAdvice().observe(viewLifecycleOwner, Observer {
            advice = it

            adviceGeneralContainer.visibility = View.VISIBLE
            progress_bar.visibility = View.GONE

            adviceItemTitle.text = advice.title
            adviceItemContent.movementMethod = LinkMovementMethod.getInstance()
            adviceItemContent.text = Html.fromHtml(advice.html[0].html)
        })

        adviceRegions.setOnClickListener {
            (context as MainActivity).showRegionsBottomSheet(this)
        }
//
//        adviceCompanies.setOnClickListener {
//            initData()
//            isCompanies = true
//            companyRecyclerView.visibility = View.VISIBLE
//            adviceItemContent.visibility = View.GONE
//            adviceItemTitle.visibility = View.GONE
//            companyListAdapter = CompanyListAdapter(companies)
//            companyRecyclerView.adapter = companyListAdapter
//        }
    }

    private fun initData() {
        companies.clear()

        companies.add(
            Company(
                "Клиникa Рacсвет",
                "Тест на антитела к COVID-19 (тест на иммунитет к вирусу) от «Клиники Рссвет» (4800 рублей)\n" +
                        "https://klinikarassvet.ru/patients/articles/testirovanie-na-antitela-k-covid-19/?utm_source=yandex.search&utm_medium=cpc&utm_campaign=51811007&utm_content=9033782539_none&utm_term=тест%20на%20covid%2019&calltouch_tm=yd_c:51811007_gb:4190147760_ad:9033782539_ph:20627441916_st:search_pt:other_p:1_s:none_dt:desktop_reg:1_ret:_apt:none&yclid=2389782048256858272"
            )
        )

        companies.add(
            Company(
                "ChaikaHealth",
                "Тест на Антитела к COVID-19 от «ChaikaHealth» (4800 реублей)"
            )
        )

        companies.add(
            Company(
                "Tест при поддержке Яндекса",
                "Бесплатный тест на COVID-19 на дому при поддержки Яндекса\n" +
                        "https://help.yandex.ru/covid19-test"
            )
        )

        companies.add(
            Company(
                "LabQuest",
                "Платный тест от LabQuest на дому (5395 рублей)\n" +
                        "https://www.labquest.ru/covid19/"
            )
        )

        companies.add(
            Company(
                "ГемоТест",
                "Платный тест от ГемоТест на дому (3000 рублей)\n" +
                        "https://www.gemotest.ru/catalog/po-laboratornym-napravleniyam/diagnostika-infektsiy/ptsr-diagnostika/individualnaya-diagnostika-vozbuditeley/koronavirus_sars_cov_2_mazok_kach/"
            )
        )
    }
}
