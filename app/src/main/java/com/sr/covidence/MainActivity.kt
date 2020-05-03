package com.sr.covidence

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private val mOnNavigationMenuItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_chat_bot_item -> {
                    //showFragment(NewsBrochuresTabbedFragment())
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
                    //showFragment(VideosFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_recomend_item -> {
                    //showFragment(OthersFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }


    fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(fragment.tag.toString())
            .replace(R.id.main_content, fragment, fragment.javaClass.simpleName)
            .commit()

    }
}
