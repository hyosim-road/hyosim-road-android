package com.hyosimroad.hamkkae.presentation.main.auth.find.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hyosimroad.hamkkae.presentation.main.auth.find.FindIdFragment
import com.hyosimroad.hamkkae.presentation.main.auth.find.FindPwFragment

class FindViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FindIdFragment()
            else -> FindPwFragment()
        }
    }
}