package com.sr.covidence.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.sr.covidence.R

fun showFragment(fragment: Fragment, fragmentManager: FragmentManager) {
    fragmentManager
        .beginTransaction()
        .addToBackStack(fragment.tag.toString())
        .replace(R.id.main_content, fragment, fragment.javaClass.simpleName)
        .commit()

}