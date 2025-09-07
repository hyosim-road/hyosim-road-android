package com.hyosim.hamkkae.presentation.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ItemTripRecordBinding
import com.hyosim.hamkkae.domain.model.TripRecord

class TripRecordAdapter : ListAdapter<TripRecord, TripRecordAdapter.TripRecordViewHolder>(TripRecordDiffCallback) {
    override fun getItemCount(): Int {
        return minOf(super.getItemCount(), 2) // 항상 최대 2개까지만 보여줌
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TripRecordViewHolder {
        val binding = ItemTripRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TripRecordViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TripRecordViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class TripRecordViewHolder(private val binding: ItemTripRecordBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(tripRecord: TripRecord) {
            with(binding){
                tvTitle.text = tripRecord.content
                tvDate.text = binding.root.context.getString(R.string.main_trip_record_during, tripRecord.startDate, tripRecord.endDate)
                tvState.text = tripRecord.state
            }
        }
    }
}

object TripRecordDiffCallback: DiffUtil.ItemCallback<TripRecord>() {
    override fun areItemsTheSame(oldItem: TripRecord, newItem: TripRecord): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: TripRecord, newItem: TripRecord): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}