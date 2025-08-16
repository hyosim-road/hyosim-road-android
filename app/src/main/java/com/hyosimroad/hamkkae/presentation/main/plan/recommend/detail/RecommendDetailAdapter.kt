package com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail.detail.RecommendDetailDetailFragment
import com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail.overview.RecommendDetailOverviewFragment
import com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail.restaurant.RecommendDetailInfoFragment
//
class RecommendDetailAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 4
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> RecommendDetailOverviewFragment()
        1 -> RecommendDetailDetailFragment()
        2 -> RecommendDetailInfoFragment.newInstance("restaurant")
        3 -> RecommendDetailInfoFragment.newInstance("accommodation")
        else -> throw IllegalStateException("Invalid tab index")
    }
}