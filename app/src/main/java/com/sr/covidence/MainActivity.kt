package com.sr.covidence

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sr.covidence.advice.AdviceFragment
import com.sr.covidence.advice.RegionsListAdapter
import com.sr.covidence.browse.BrowseFragment
import com.sr.covidence.chat_bot.ChatBotFragment
import com.sr.covidence.journal.JournalFragment
import com.sr.covidence.login.AuthorizationFragment
import com.sr.covidence.models.model.Region
import com.sr.covidence.profile.ProfileFragment
import com.sr.covidence.utils.custom.OnRegionItemClickListener
import com.sr.covidence.utils.showFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.advice_regions_bottom_sheet.*

class MainActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences
    private lateinit var adviceRegionsBottomSheetBehavior: BottomSheetBehavior<*>

    private var regions = ArrayList<Region>()
    private lateinit var regionsListAdapter: RegionsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pref = getSharedPreferences("sharedPreferences", MODE_PRIVATE)

        navigation_menu.setOnNavigationItemSelectedListener(mOnNavigationMenuItemSelectedListener)
        navigation_menu.selectedItemId = R.id.navigation_chat_bot_item

        initAdviceRegionsBottomSheet()
        initData()
    }

    private val mOnNavigationMenuItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.navigation_advice_item -> {
                    showFragment(AdviceFragment(), supportFragmentManager)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_browse_item -> {
                    showFragment(BrowseFragment(), supportFragmentManager)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_chat_bot_item -> {
                    showFragment(ChatBotFragment(), supportFragmentManager)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_journal_item -> {
                    showFragment(JournalFragment(), supportFragmentManager)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile_item -> {
                    if (pref.getBoolean("isLogin", false)) {
                        showFragment(ProfileFragment(), supportFragmentManager)
                    } else {
                        showFragment(
                            AuthorizationFragment(),
                            supportFragmentManager
                        )
                    }
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun initAdviceRegionsBottomSheet() {
        adviceRegionsBottomSheetBehavior = BottomSheetBehavior.from(adviceRegionsBottomSheet)
        adviceRegionsBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    fun showRegionsBottomSheet(
        itemClickListener: OnRegionItemClickListener
    ) {
        regionsListAdapter = RegionsListAdapter(regions, itemClickListener)
        regionRecycler.adapter = regionsListAdapter
        adviceRegionsBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun hideRegionsBottomSheet() {
        adviceRegionsBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun initData() {
        regions.add(
            Region(
                "Город Пятигорск",
                "<html><head></head><body><p>1. Ввести ограничительные мероприятия (карантин) на территории города-курорта Пятигорска Ставропольского края (далее - город Пятигорск) в границах, определенных Законом Ставропольского края от 7 июля 2004 г.</p>" +
                        "<p><strong>№ 58-кз «Об установлении границы муниципального образования города-курорта Пятигорска Ставропольского края\"</strong> (далее - ограничительные мероприятия (карантин), до их отмены.</p>" +
                        "<p>2. На период действия ограничительных мероприятий (карантина):</p>" +
                        "<p>2.1. Запретить:</p>" +
                        "<p>2.1.1. Въезд в город Пятигорск и выезд из города Пятигорска всех видов автомобильного транспорта (в том числе следующего транзитом), за исключением имеющих специальный пропуск служебных транспортных средств Министерства обороны Российской Федерации, Министерства Российской</p>" +
                        "<p>Федерации по делам гражданской обороны, чрезвычайным ситуациям и ликвидации последствий стихийных бедствий, правоохранительных органов и специальных подразделений, организаций, деятельность которых является критически важной для решения вопросов жизнеобеспечения населения города Пятигорска, а также имеющих специальный пропуск личных транспортных средств сотрудников (работников) указанных органов и организаций и транспортных средств, обеспечивающих функционирование указанных органов и организаций.</p>" +
                        "<p>2.1.2. Проведение на территории города Пятигорска досуговых, культовых, развлекательных, зрелищных, культурных, физкультурно-спортивных, выставочных, просветительских, рекламных и иных подобных мероприятий с очным присутствием граждан.</p>" +
                        "<p>2.1.3. Посещение обучающимися образовательных организаций высшего образования, дополнительного профессионального образования, среднего профессионального образования, а также образовательных организаций, реализующих программы начального общего, основного общего и среднего общего образования и дополнительного образования, и обязать указанные организации организовать реализацию образовательных программ с использованием различных образовательных технологий, позволяющих обеспечить взаимодействие обучающихся и педагогических работников опосредованно (на расстоянии), в том числе с применением электронного обучения и дистанционных образовательных технологий.</p>" +
                        "<p>2.1.4. Посещение детьми образовательных организаций, реализующих образовательные программы дошкольного образования, за исключением муниципальных бюджетных дошкольных образовательных учреждений, на базе которых организована деятельность дежурных групп.</p>" +
                        "<p>2.1.5. Посещение гражданами объектов (территорий), находящихся в собственности религиозных организаций, а равно используемых ими на ином законном основании зданий, строений, сооружений, помещений, земельных участков, предназначенных для богослужений, молитвенных и религиозных собраний, религиозного почитания (паломничества).</p>" +
                        "<p>2.1.6. Деятельность медицинских организаций всех форм собственности, за исключением деятельности медицинских организаций по оказанию экстренной (неотложной) медицинской помощи с соблюдением всех установленных рекомендаций по предупреждению распространения новой коронавирусной инфекции COVID-2019.</p>" +
                        "<p>2.1.7. Прием государственными медицинскими организациями плановых пациентов,</p>" +
                        "<p>2.1.8. Работу на территории города Пятигорска организации, за исключением организаций, деятельность которых является критически важной для решения вопросов жизнеобеспечения населения города Пятигорска.</p>" +
                        "<p>2.1.9. Деятельность торговых объектов с массовым очным скоплением людей, в том числе рынков, торговых комплексов, ярмарок.</p>" +
                        "<p>2.2. Ограничить передвижение по территории города Пятигорска общественного транспорта, в котором невозможно обеспечить режим социального дистанцирования.</p></body>"
            )
        )

        regions.add(
            Region(
                "Город Москва", "<p>Обязывает:</p>" +
                        "<p>Граждан не покидать места проживания (пребывания), за исключением случаев обращения за экстренной (неотложной) медицинской помощью и случаев иной прямой угрозы жизни и здоровью, случаев следования к месту (от места) осуществления деятельности (в том числе работы), которая не приостановлена в соответствии с настоящим указом, осуществления деятельности, связанной с передвижением по территории города Москвы, в случае если такое передвижение непосредственно связано с осуществлением деятельности, которая не приостановлена в соответствии с настоящим указом (в том числе оказанием транспортных услуг и услуг доставки), а также следования к ближайшему месту приобретения товаров, работ, услуг, реализация которых не ограничена в соответствии с настоящим указом, выгула домашних животных на расстоянии, не превышающем 100 метров от места проживания (пребывания), выноса отходов до ближайшего места накопления отходов.</p>" +
                        "<p>Ограничения, установленные настоящим пунктом, не распространяются на случаи оказания медицинской помощи, деятельность правоохранительных органов, органов по делам гражданской обороны и чрезвычайным ситуациям и подведомственных им организаций, органов по надзору в сфере защиты прав потребителей и благополучия человека, иных органов в части действий, непосредственно направленных на защиту жизни, здоровья и иных прав и свобод граждан, в том числе противодействие преступности, охраны общественного порядка, собственности и обеспечения общественной безопасности.<p>"
            )
        )

        regions.add(
            Region(
                "Московская область", "<p>Обязывает:</p>" +
                        "<p>Граждан не покидать места проживания (пребывания), за исключением случаев обращения за экстренной (неотложной) медицинской помощью и случаев иной прямой угрозы жизни и здоровью, случаев следования к месту (от места) осуществления деятельности (в том числе работы), которая не приостановлена в соответствии с настоящим указом, осуществления деятельности, связанной с передвижением по территории города Москвы, в случае если такое передвижение непосредственно связано с осуществлением деятельности, которая не приостановлена в соответствии с настоящим указом (в том числе оказанием транспортных услуг и услуг доставки), а также следования к ближайшему месту приобретения товаров, работ, услуг, реализация которых не ограничена в соответствии с настоящим указом, выгула домашних животных на расстоянии, не превышающем 100 метров от места проживания (пребывания), выноса отходов до ближайшего места накопления отходов.</p>" +
                        "<p>Ограничения, установленные настоящим пунктом, не распространяются на случаи оказания медицинской помощи, деятельность правоохранительных органов, органов по делам гражданской обороны и чрезвычайным ситуациям и подведомственных им организаций, органов по надзору в сфере защиты прав потребителей и благополучия человека, иных органов в части действий, непосредственно направленных на защиту жизни, здоровья и иных прав и свобод граждан, в том числе противодействие преступности, охраны общественного порядка, собственности и обеспечения общественной безопасности.<p>"
            )
        )
    }
}
