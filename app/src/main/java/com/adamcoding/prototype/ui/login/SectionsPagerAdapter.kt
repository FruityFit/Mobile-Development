package com.adamcoding.prototype.ui.login

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SigninFragment.newInstance(position + 1)
            1 -> SignupFragment.newInstance(position + 1)
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}