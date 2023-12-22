package com.adamcoding.prototype.ui.splash

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SplashPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragmentList = mutableListOf<Fragment>()

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }
}