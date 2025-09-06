package com.hyosim.hamkkae.presentation.main.plan.recommend.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import com.hyosim.hamkkae.data.response_dto.plan.CourseRecommendResponseData
import com.hyosim.hamkkae.databinding.ItemRecommendCoursePlaceBinding
import com.hyosim.hamkkae.domain.model.Course
import java.text.SimpleDateFormat
import java.util.Locale

class RecommendCoursePlaceAdapter:
    ListAdapter<AiCourseRecommendResponseDto.Itinerary.Attraction, RecommendCoursePlaceAdapter.RecommendCoursePlaceViewHolder>(RecommendCoursePlaceDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendCoursePlaceViewHolder {
        val binding = ItemRecommendCoursePlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendCoursePlaceViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecommendCoursePlaceViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class RecommendCoursePlaceViewHolder(private val binding: ItemRecommendCoursePlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(place: AiCourseRecommendResponseDto.Itinerary.Attraction){
            with(binding){
                tvPlace.text = place.name
                tvTime.text = formatTimeRange(place.startTime, place.endTime)
                ivIcon.load(R.drawable.ic_location_gray_24)
            }
        }

        private fun formatTimeRange(start: String, end: String): String {
            return try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA)
                val outputFormat = SimpleDateFormat("HH:mm", Locale.KOREA)

                val startDate = inputFormat.parse(start)
                val endDate = inputFormat.parse(end)

                if (startDate != null && endDate != null) {
                    "${outputFormat.format(startDate)} ~ ${outputFormat.format(endDate)}"
                } else {
                    ""
                }
            } catch (e: Exception) {
                ""
            }
        }

    }
}

object RecommendCoursePlaceDiffCallback : DiffUtil.ItemCallback<AiCourseRecommendResponseDto.Itinerary.Attraction>() {
    override fun areItemsTheSame(oldItem: AiCourseRecommendResponseDto.Itinerary.Attraction, newItem: AiCourseRecommendResponseDto.Itinerary.Attraction): Boolean {
        return oldItem.startTime == newItem.startTime // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: AiCourseRecommendResponseDto.Itinerary.Attraction, newItem: AiCourseRecommendResponseDto.Itinerary.Attraction): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}