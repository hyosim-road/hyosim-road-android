package com.hyosimroad.hamkkae.presentation.main.plan.trip_start.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hyosimroad.hamkkae.domain.model.Course

class TripStartAdapter {
}

object TripStartDiffCallback : DiffUtil.ItemCallback<Course>() {
    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}