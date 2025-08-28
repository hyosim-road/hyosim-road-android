package com.hyosim.hamkkae.presentation.main.plan.recommend.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ItemRecommendCoursePlaceBinding
import com.hyosim.hamkkae.domain.model.Course

class RecommendCoursePlaceAdapter:
    ListAdapter<Course.Place, RecommendCoursePlaceAdapter.RecommendCoursePlaceViewHolder>(RecommendCoursePlaceDiffCallback) {
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
        fun bind(place: Course.Place){
            with(binding){
                tvPlace.text = place.name
                tvTime.text = place.time
                ivIcon.load(R.drawable.ic_location_gray_24)
            }
        }
    }
}

object RecommendCoursePlaceDiffCallback : DiffUtil.ItemCallback<Course.Place>() {
    override fun areItemsTheSame(oldItem: Course.Place, newItem: Course.Place): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: Course.Place, newItem: Course.Place): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}