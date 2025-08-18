package com.hyosimroad.hamkkae.presentation.main.home.adapter.today_schedule

import androidx.recyclerview.widget.DiffUtil
import com.hyosimroad.hamkkae.domain.model.TodaySchedule

object TodayScheduleDiffCallback : DiffUtil.ItemCallback<TodaySchedule>() {
    override fun areItemsTheSame(oldItem: TodaySchedule, newItem: TodaySchedule): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: TodaySchedule, newItem: TodaySchedule): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}