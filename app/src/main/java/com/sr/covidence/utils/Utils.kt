package com.sr.covidence.utils

import android.content.Context
import android.content.res.Resources
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

fun dpToPx(context: Context, dp: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}