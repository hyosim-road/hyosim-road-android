package com.hyosim.hamkkae.presentation.main.trip_detail.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.data.response_dto.home.ProgressTripResponseDto
import com.hyosim.hamkkae.databinding.ItemTripDetailScheduleBinding
import com.hyosim.hamkkae.domain.model.TodaySchedule
import com.hyosim.hamkkae.presentation.main.home.adapter.TodayScheduleAdapter
import com.hyosim.hamkkae.util.StateConstants.TYPE_BEFORE_STARTING
import com.hyosim.hamkkae.util.StateConstants.TYPE_COMPLETE
import com.hyosim.hamkkae.util.StateConstants.TYPE_IN_PROCESS
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TripDetailScheduleAdapter :
    ListAdapter<ProgressTripResponseDto.Itinerary, TripDetailScheduleAdapter.TripDetailScheduleViewHolder>(
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
        holder.bind(getItem(position), position)
    }

    inner class TripDetailScheduleViewHolder(private val binding: ItemTripDetailScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(course: ProgressTripResponseDto.Itinerary, position:Int) {
            val context = binding.root.context
            val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA)
            val timeFormatter = SimpleDateFormat("HH:mm", Locale.KOREA) // UI 표시용
            val now = System.currentTimeMillis()

            with(binding){
                tvDay.text = context.getString(R.string.trip_detail_day, position+1)

                val isToday = course.isToday()
                tvToday.visibility = if (isToday) View.VISIBLE else View.GONE

                tvDate.apply{
                    text = course.day
                    setTextColor(if(isToday) context.getColor(R.color.primary_orange) else context.getColor(R.color.text_secondary_gray))
                    setBackgroundResource(if(isToday) R.drawable.bg_day_yellow else R.drawable.bg_place_outline)
                }

                val todaySchedules = course.attractions.map { attr ->
                    val startDate = dateTimeFormatter.parse(attr.startTime)
                    val endDate = dateTimeFormatter.parse(attr.endTime)

                    val startMillis = startDate?.time ?: 0L
                    val endMillis = endDate?.time ?: 0L

                    val status = when {
                        !isToday && course.day.substring(0, 10) < SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(Date(now)) ->
                            TYPE_COMPLETE // 과거 날짜
                        !isToday && course.day.substring(0, 10) > SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(Date(now)) ->
                            TYPE_BEFORE_STARTING // 미래 날짜
                        now in startMillis..endMillis -> TYPE_IN_PROCESS // 오늘 + 진행중
                        now < startMillis -> TYPE_BEFORE_STARTING               // 오늘 + 시작 전
                        else -> TYPE_COMPLETE                                  // 오늘 + 끝남
                    }

                    TodaySchedule(
                        id = attr.order,
                        place = attr.name,
                        keyword = attr.description ?: "",
                        startTime = startDate?.let { timeFormatter.format(it) } ?: attr.startTime, // "HH:mm"
                        endTime = endDate?.let { timeFormatter.format(it) } ?: attr.endTime,       // "HH:mm"
                        status = status
                    )
                }

                Timber.d("todaySchedules: $todaySchedules")
                rvSchedule.adapter = TodayScheduleAdapter(false).apply {
                    submitList(todaySchedules)
                }
            }
        }

        fun ProgressTripResponseDto.Itinerary.isToday(): Boolean {
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            val todayStr = dateFormatter.format(Date(System.currentTimeMillis()))
            return this.day.substring(0, 10) == todayStr
        }
    }
}

object TripDetailScheduleDiffCallback : DiffUtil.ItemCallback<ProgressTripResponseDto.Itinerary>() {
    override fun areItemsTheSame(oldItem: ProgressTripResponseDto.Itinerary, newItem: ProgressTripResponseDto.Itinerary): Boolean {
        return oldItem.day == newItem.day // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: ProgressTripResponseDto.Itinerary, newItem: ProgressTripResponseDto.Itinerary): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}