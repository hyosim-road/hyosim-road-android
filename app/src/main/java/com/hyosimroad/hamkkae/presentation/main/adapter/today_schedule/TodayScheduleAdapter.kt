package com.hyosimroad.hamkkae.presentation.main.adapter.today_schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ItemTripScheduleBinding
import com.hyosimroad.hamkkae.domain.model.TodaySchedule
import com.hyosimroad.hamkkae.util.StateConstants.TYPE_BEFORE_STARTING
import com.hyosimroad.hamkkae.util.StateConstants.TYPE_COMPLETE
import com.hyosimroad.hamkkae.util.StateConstants.TYPE_IN_PROCESS

class TodayScheduleAdapter : ListAdapter<TodaySchedule, TodayScheduleAdapter.TodayScheduleViewHolder>(TodayScheduleDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodayScheduleViewHolder {
        val binding = ItemTripScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodayScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TodayScheduleViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class TodayScheduleViewHolder(private val binding: ItemTripScheduleBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(schedule: TodaySchedule) {
            with(binding){
                when(schedule.status){
                    TYPE_COMPLETE -> {
                        ivIcon.load(R.drawable.ic_complete_blue)
                    }
                    TYPE_IN_PROCESS -> {
                        ivIcon.load(R.drawable.ic_current_orange)
                        tvCurrent.visibility=View.VISIBLE
                    }
                    TYPE_BEFORE_STARTING -> {
                        ivIcon.load(R.drawable.bg_schedule_gray)
                        tvNumber.visibility= View.VISIBLE
                        tvNumber.text = schedule.id.toString()
                    }
                }

                tvPlace.text = schedule.place
                tvKeyword.text = schedule.keyword
                tvTime.text = binding.root.context.getString(R.string.main_time, schedule.startTime, schedule.endTime)

            }
        }
    }
}