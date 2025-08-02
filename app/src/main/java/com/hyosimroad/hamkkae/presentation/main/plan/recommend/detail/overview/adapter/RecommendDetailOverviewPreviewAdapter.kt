package com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail.overview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ItemTripScheduleBinding
import com.hyosimroad.hamkkae.domain.model.HighLights
import com.hyosimroad.hamkkae.domain.model.TodaySchedule

class RecommendDetailOverviewPreviewAdapter: ListAdapter<TodaySchedule, RecommendDetailOverviewPreviewAdapter.RecommendDetailOverviewPreviewViewHolder>(RecommendDetailOverviewPreviewDiffCallback) {
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
        fun bind(preview: TodaySchedule) {
            with(binding) {
                ivBgGray.visibility = View.INVISIBLE
                ivIcon.load(R.drawable.ic_current_orange)
                tvPlace.text = preview.place
                tvTime.text = "${preview.startTime} ~ ${preview.endTime}"
                tvKeyword.text = preview.keyword
            }
        }
    }
}

object RecommendDetailOverviewPreviewDiffCallback : DiffUtil.ItemCallback<TodaySchedule>() {
    override fun areItemsTheSame(oldItem: TodaySchedule, newItem: TodaySchedule): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: TodaySchedule, newItem: TodaySchedule): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}