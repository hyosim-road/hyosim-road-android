package com.hyosimroad.hamkkae.presentation.main.home.adapter.trip_record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ItemTripRecordBinding
import com.hyosimroad.hamkkae.domain.model.TripRecord

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