package com.hyosim.hamkkae.presentation.main.plan.recommend.detail.overview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ItemRecommendCoursePlaceBinding
import com.hyosim.hamkkae.domain.model.HighLights

class RecommendDetailOverviewHighlightAdapter:
    ListAdapter<HighLights, RecommendDetailOverviewHighlightAdapter.RecommendDetailOverviewHighlightViewHolder>(RecommendDetailOverviewHighlightDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendDetailOverviewHighlightViewHolder {
        val binding = ItemRecommendCoursePlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendDetailOverviewHighlightViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecommendDetailOverviewHighlightViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class RecommendDetailOverviewHighlightViewHolder(private val binding: ItemRecommendCoursePlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(highlight: HighLights){
            with(binding){
                tvTime.visibility= View.GONE
                tvNumber.visibility=View.VISIBLE

                ivIcon.load(R.drawable.bg_schedule_yellow)
                tvNumber.text=highlight.id.toString()
                tvPlace.text=highlight.content
            }
        }
    }
}

object RecommendDetailOverviewHighlightDiffCallback : DiffUtil.ItemCallback<HighLights>() {
    override fun areItemsTheSame(oldItem: HighLights, newItem: HighLights): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: HighLights, newItem: HighLights): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}