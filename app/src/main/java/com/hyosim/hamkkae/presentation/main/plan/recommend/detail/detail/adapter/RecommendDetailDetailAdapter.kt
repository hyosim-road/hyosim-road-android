package com.hyosim.hamkkae.presentation.main.plan.recommend.detail.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import com.hyosim.hamkkae.databinding.ItemRecommendDetailDetailBinding
import com.hyosim.hamkkae.domain.model.CourseDetail

class RecommendDetailDetailAdapter :
    ListAdapter<AiCourseRecommendResponseDto.Itinerary.Attraction, RecommendDetailDetailAdapter.RecommendDetailDetailViewHolder>(
        RecommendDetailDetailDiffCallback
    ) {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendDetailDetailViewHolder {
        val binding = ItemRecommendDetailDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecommendDetailDetailViewHolder(binding, viewPool)
    }

    override fun onBindViewHolder(
        holder: RecommendDetailDetailViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class RecommendDetailDetailViewHolder(
        private val binding: ItemRecommendDetailDetailBinding,
        private val viewPool: RecyclerView.RecycledViewPool
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val recommendDetailDetailInfoAdapter = RecommendDetailDetailInfoAdapter()

        init {
            binding.rvInfo.apply {
                layoutManager =
                    LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
                adapter = recommendDetailDetailInfoAdapter
                setRecycledViewPool(viewPool)
                isNestedScrollingEnabled = false
                overScrollMode = View.OVER_SCROLL_NEVER
            }
        }

        fun bind(courseDetail: AiCourseRecommendResponseDto.Itinerary.Attraction) {
            with(binding) {
                tvName.text = courseDetail.name
                tvDescription.text = courseDetail.description
                ivImage.load(R.drawable.image_trip)

                val courseDetail = listOf(
                    courseDetail.address,
                    courseDetail.phone,
                    "${courseDetail.priceKrw}원"
                )
                recommendDetailDetailInfoAdapter.submitList(courseDetail)

                //recommendDetailDetailInfoAdapter.submitList(courseDetail)
            }
        }
    }
}

object RecommendDetailDetailDiffCallback : DiffUtil.ItemCallback<AiCourseRecommendResponseDto.Itinerary.Attraction>() {
    override fun areItemsTheSame(oldItem: AiCourseRecommendResponseDto.Itinerary.Attraction, newItem: AiCourseRecommendResponseDto.Itinerary.Attraction): Boolean {
        return oldItem.startTime == newItem.startTime
    }

    override fun areContentsTheSame(oldItem: AiCourseRecommendResponseDto.Itinerary.Attraction, newItem: AiCourseRecommendResponseDto.Itinerary.Attraction): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}