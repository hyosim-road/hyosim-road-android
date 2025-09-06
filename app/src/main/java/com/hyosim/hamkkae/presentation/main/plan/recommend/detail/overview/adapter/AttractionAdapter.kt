package com.hyosim.hamkkae.presentation.main.plan.recommend.detail.overview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import com.hyosim.hamkkae.databinding.ItemTripScheduleBinding
import java.text.SimpleDateFormat
import java.util.Locale

class AttractionAdapter :
    ListAdapter<AiCourseRecommendResponseDto.Itinerary.Attraction, AttractionAdapter.AttractionViewHolder>(
        AttractionDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionViewHolder {
        val binding = ItemTripScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AttractionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AttractionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AttractionViewHolder(private val binding: ItemTripScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(attraction: AiCourseRecommendResponseDto.Itinerary.Attraction) {
            with(binding) {
                ivBgGray.visibility = View.INVISIBLE
                ivIcon.load(R.drawable.ic_current_orange)
                tvPlace.text = attraction.name
                tvTime.text = formatTimeRange(attraction.startTime, attraction.endTime)
                //tvKeyword.text = "" // API에서 keyword 필드 있으면 바인딩
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

object AttractionDiffCallback :
    DiffUtil.ItemCallback<AiCourseRecommendResponseDto.Itinerary.Attraction>() {
    override fun areItemsTheSame(oldItem: AiCourseRecommendResponseDto.Itinerary.Attraction, newItem: AiCourseRecommendResponseDto.Itinerary.Attraction): Boolean {
        return oldItem.order == newItem.order
    }

    override fun areContentsTheSame(oldItem: AiCourseRecommendResponseDto.Itinerary.Attraction, newItem: AiCourseRecommendResponseDto.Itinerary.Attraction): Boolean {
        return oldItem == newItem
    }
}
