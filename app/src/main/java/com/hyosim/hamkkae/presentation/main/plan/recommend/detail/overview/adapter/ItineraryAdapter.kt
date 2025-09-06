package com.hyosim.hamkkae.presentation.main.plan.recommend.detail.overview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import com.hyosim.hamkkae.databinding.ItemItineraryDayBinding

class ItineraryAdapter :
    ListAdapter<AiCourseRecommendResponseDto.Itinerary, ItineraryAdapter.ItineraryViewHolder>(
        ItineraryDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItineraryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemItineraryDayBinding.inflate(inflater, parent, false)
        return ItineraryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItineraryViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ItineraryViewHolder(private val binding: ItemItineraryDayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itinerary: AiCourseRecommendResponseDto.Itinerary, position: Int) {
            binding.tvFirstDay.text = "${position + 1}번째 날"

            val childAdapter = AttractionAdapter()
            binding.rvPreview.adapter = childAdapter
            childAdapter.submitList(itinerary.attractions)
        }
    }
}

object ItineraryDiffCallback :
    DiffUtil.ItemCallback<AiCourseRecommendResponseDto.Itinerary>() {
    override fun areItemsTheSame(oldItem: AiCourseRecommendResponseDto.Itinerary, newItem: AiCourseRecommendResponseDto.Itinerary): Boolean {
        return oldItem.day == newItem.day
    }

    override fun areContentsTheSame(oldItem: AiCourseRecommendResponseDto.Itinerary, newItem: AiCourseRecommendResponseDto.Itinerary): Boolean {
        return oldItem == newItem
    }
}
