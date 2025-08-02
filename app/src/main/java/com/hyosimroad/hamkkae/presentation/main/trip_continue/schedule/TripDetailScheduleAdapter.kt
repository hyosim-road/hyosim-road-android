package com.hyosimroad.hamkkae.presentation.main.trip_continue.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ItemTripDetailScheduleBinding
import com.hyosimroad.hamkkae.domain.model.Course
import com.hyosimroad.hamkkae.domain.model.TotalCourse
import com.hyosimroad.hamkkae.presentation.main.adapter.today_schedule.TodayScheduleAdapter

class TripDetailScheduleAdapter :
    ListAdapter<TotalCourse, TripDetailScheduleAdapter.TripDetailScheduleViewHolder>(
        TripDetailScheduleDiffCallback
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TripDetailScheduleViewHolder {
        val binding = ItemTripDetailScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TripDetailScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TripDetailScheduleViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class TripDetailScheduleViewHolder(private val binding: ItemTripDetailScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(totalCourse: TotalCourse) {
            with(binding){
                tvDay.text = binding.root.context.getString(R.string.trip_detail_day, totalCourse.id)
                tvToday.visibility = (if(totalCourse.id==1) View.VISIBLE else View.GONE)
                tvDate.text = totalCourse.date

                tvDate.setTextColor(if(totalCourse.id==1) binding.root.context.getColor(R.color.primary_orange) else binding.root.context.getColor(R.color.text_secondary_gray))
                tvDate.setBackgroundResource(if(totalCourse.id==1) R.drawable.bg_day_yellow else R.drawable.bg_place_outline)

                rvSchedule.adapter = TodayScheduleAdapter(false).apply {
                    submitList(totalCourse.course)
                }
            }
        }
    }
}

object TripDetailScheduleDiffCallback : DiffUtil.ItemCallback<TotalCourse>() {
    override fun areItemsTheSame(oldItem: TotalCourse, newItem: TotalCourse): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: TotalCourse, newItem: TotalCourse): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}