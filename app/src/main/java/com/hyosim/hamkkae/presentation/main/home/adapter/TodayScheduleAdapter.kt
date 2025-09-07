package com.hyosim.hamkkae.presentation.main.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ItemTripScheduleBinding
import com.hyosim.hamkkae.domain.model.TodaySchedule
import com.hyosim.hamkkae.util.StateConstants.TYPE_BEFORE_STARTING
import com.hyosim.hamkkae.util.StateConstants.TYPE_COMPLETE
import com.hyosim.hamkkae.util.StateConstants.TYPE_IN_PROCESS
import timber.log.Timber

class TodayScheduleAdapter(
    private val bgVisibility:Boolean
) : ListAdapter<TodaySchedule, TodayScheduleAdapter.TodayScheduleViewHolder>(TodayScheduleDiffCallback) {
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
            Timber.d("schedule: $schedule")
            with(binding){
                tvCurrent.visibility = View.GONE
                tvNumber.visibility = View.GONE

                when (schedule.status) {
                    TYPE_COMPLETE -> {
                        ivIcon.load(R.drawable.ic_complete_blue)
                    }
                    TYPE_IN_PROCESS -> {
                        ivIcon.load(R.drawable.ic_current_orange)
                        tvCurrent.visibility = View.VISIBLE
                    }
                    TYPE_BEFORE_STARTING -> {
                        ivIcon.setImageResource(R.drawable.bg_schedule_gray)
                        tvNumber.visibility = View.VISIBLE
                        tvNumber.text = schedule.id.toString()
                    }
                }

                tvPlace.text = schedule.place
                //tvKeyword.text = schedule.keyword
                tvTime.text = binding.root.context.getString(R.string.main_time, schedule.startTime, schedule.endTime)

                if(!bgVisibility) ivBgGray.visibility = View.INVISIBLE
                tvKeyword.visibility= View.GONE
            }
        }
    }
}

object TodayScheduleDiffCallback : DiffUtil.ItemCallback<TodaySchedule>() {
    override fun areItemsTheSame(oldItem: TodaySchedule, newItem: TodaySchedule): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: TodaySchedule, newItem: TodaySchedule): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}