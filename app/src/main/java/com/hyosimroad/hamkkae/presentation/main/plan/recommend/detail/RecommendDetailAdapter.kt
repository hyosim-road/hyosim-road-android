package com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail.detail.RecommendDetailDetailFragment
import com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail.overview.RecommendDetailOverviewFragment
import com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail.tip.RecommendDetailTipFragment
//
class RecommendDetailAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> RecommendDetailOverviewFragment()
        1 -> RecommendDetailDetailFragment()
        else -> RecommendDetailTipFragment()
    }
}