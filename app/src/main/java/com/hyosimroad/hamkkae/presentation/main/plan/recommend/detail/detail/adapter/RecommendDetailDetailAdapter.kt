package com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyosimroad.hamkkae.databinding.ItemRecommendDetailDetailBinding
import com.hyosimroad.hamkkae.domain.model.CourseDetail

class RecommendDetailDetailAdapter :
    ListAdapter<CourseDetail, RecommendDetailDetailAdapter.RecommendDetailDetailViewHolder>(
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

        fun bind(courseDetail: CourseDetail) {
            with(binding) {
                tvName.text = courseDetail.name
                tvDescription.text = courseDetail.description
                ivImage.load(courseDetail.image)

                recommendDetailDetailInfoAdapter.submitList(courseDetail.info)
            }
        }
    }
}

object RecommendDetailDetailDiffCallback : DiffUtil.ItemCallback<CourseDetail>() {
    override fun areItemsTheSame(oldItem: CourseDetail, newItem: CourseDetail): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: CourseDetail, newItem: CourseDetail): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}