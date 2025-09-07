package com.hyosim.hamkkae.presentation.main.trip_records.detail.visited

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ItemTripDetailScheduleBinding
import com.hyosim.hamkkae.domain.model.TotalCourse
import com.hyosim.hamkkae.presentation.main.home.adapter.TodayScheduleAdapter

class VisitedAdapter :
    ListAdapter<TotalCourse, VisitedAdapter.VisitedViewHolder>(
        VisitedDiffCallback
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VisitedViewHolder {
        val binding = ItemTripDetailScheduleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VisitedViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: VisitedViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class VisitedViewHolder(private val binding: ItemTripDetailScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(totalCourse: TotalCourse) {
            with(binding) {
                tvDay.text =
                    binding.root.context.getString(R.string.trip_detail_day, totalCourse.id)
                tvToday.visibility = (if (totalCourse.id == 1) View.VISIBLE else View.GONE)
                tvDate.text = totalCourse.date

                tvDate.setTextColor(
                    if (totalCourse.id == 1) binding.root.context.getColor(R.color.primary_orange) else binding.root.context.getColor(
                        R.color.text_secondary_gray
                    )
                )
                tvDate.setBackgroundResource(if (totalCourse.id == 1) R.drawable.bg_day_yellow else R.drawable.bg_place_outline)

                rvSchedule.adapter = TodayScheduleAdapter(false).apply {
                    submitList(totalCourse.course)
                }
            }
        }
    }
}

object VisitedDiffCallback : DiffUtil.ItemCallback<TotalCourse>() {
    override fun areItemsTheSame(oldItem: TotalCourse, newItem: TotalCourse): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: TotalCourse, newItem: TotalCourse): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}