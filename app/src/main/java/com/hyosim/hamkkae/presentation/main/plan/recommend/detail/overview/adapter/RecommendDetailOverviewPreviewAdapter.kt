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
import com.hyosim.hamkkae.domain.model.TodaySchedule

class RecommendDetailOverviewPreviewAdapter: ListAdapter<AiCourseRecommendResponseDto.Itinerary, RecommendDetailOverviewPreviewAdapter.RecommendDetailOverviewPreviewViewHolder>(RecommendDetailOverviewPreviewDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendDetailOverviewPreviewViewHolder {
        val binding = ItemTripScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendDetailOverviewPreviewViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecommendDetailOverviewPreviewViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class RecommendDetailOverviewPreviewViewHolder(private val binding: ItemTripScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(preview: AiCourseRecommendResponseDto.Itinerary) {
            with(binding) {
                ivBgGray.visibility = View.INVISIBLE
                ivIcon.load(R.drawable.ic_current_orange)
               /* tvPlace.text = preview.place
                tvTime.text = "${preview.startTime} ~ ${preview.endTime}"
                tvKeyword.text = preview.keyword*/
            }
        }
    }
}

object RecommendDetailOverviewPreviewDiffCallback : DiffUtil.ItemCallback<AiCourseRecommendResponseDto.Itinerary>() {
    override fun areItemsTheSame(oldItem: AiCourseRecommendResponseDto.Itinerary, newItem: AiCourseRecommendResponseDto.Itinerary): Boolean {
        return oldItem.day == newItem.day // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: AiCourseRecommendResponseDto.Itinerary, newItem: AiCourseRecommendResponseDto.Itinerary): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}