package com.hyosimroad.hamkkae.presentation.main.trip_records.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hyosimroad.hamkkae.presentation.main.trip_records.detail.answers.AnswersFragment
import com.hyosimroad.hamkkae.presentation.main.trip_records.detail.overview.OverviewFragment
import com.hyosimroad.hamkkae.presentation.main.trip_records.detail.visited.VisitedFragment

class TripRecordsDetailAdapter : FragmentStateAdapter {
    constructor(activity: FragmentActivity) : super(activity)

    override fun getItemCount() = 3
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> OverviewFragment()
        1 -> VisitedFragment()
        else -> AnswersFragment()
    }
}