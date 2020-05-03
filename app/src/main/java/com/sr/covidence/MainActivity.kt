package com.sr.covidence

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sr.covidence.chat_bot.ChatBotFragment
import com.sr.covidence.login.AuthorizationFragment
import com.sr.covidence.profile.ProfileFragment
import com.sr.covidence.utils.showFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pref = getSharedPreferences("sharedPreferences", MODE_PRIVATE)

        navigation_menu.setOnNavigationItemSelectedListener(mOnNavigationMenuItemSelectedListener)

        showFragment(ChatBotFragment(), supportFragmentManager)
    }

    private val mOnNavigationMenuItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_chat_bot_item -> {
                    showFragment(ChatBotFragment(), supportFragmentManager)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_note_item -> {
                    //showFragment(CalendarFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_map_item -> {
                    //showFragment(ServicesFragment())
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
                R.id.navigation_recomend_item -> {
                    //showFragment(OthersFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}


//<iframe src="https://coronavirus-monitor.ru/map-moscow" frameBorder="0" height="400" width="900" style="max-width: 100%;"></iframe>