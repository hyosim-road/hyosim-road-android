package com.hyosim.hamkkae.presentation.main.plan.trip_start

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ItemTripScheduleBinding
import com.hyosim.hamkkae.domain.model.TodaySchedule
import com.hyosim.hamkkae.presentation.main.home.adapter.TodayScheduleDiffCallback

class TripStartAdapter(
    private val bgVisibility:Boolean
) : ListAdapter<TodaySchedule, TripStartAdapter.TripStartViewHolder>(TodayScheduleDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TripStartViewHolder {
        val binding = ItemTripScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TripStartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TripStartViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class TripStartViewHolder(private val binding: ItemTripScheduleBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(schedule: TodaySchedule) {
            with(binding){
                ivIcon.load(R.drawable.bg_schedule_gray)
                tvNumber.visibility= View.VISIBLE
                tvNumber.text = schedule.id.toString()

                tvPlace.text = schedule.place
                tvKeyword.visibility = View.GONE
                tvTime.text = binding.root.context.getString(R.string.main_time, schedule.startTime, schedule.endTime)

                if(!bgVisibility) ivBgGray.visibility = View.INVISIBLE
            }
        }
    }
}