package com.hyosim.hamkkae.presentation.main.home.adapter.trip_record

import androidx.recyclerview.widget.DiffUtil
import com.hyosim.hamkkae.domain.model.TripRecord

object TripRecordDiffCallback: DiffUtil.ItemCallback<TripRecord>() {
    override fun areItemsTheSame(oldItem: TripRecord, newItem: TripRecord): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: TripRecord, newItem: TripRecord): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}