package com.hyosim.hamkkae.presentation.main.plan.recommend.detail.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ItemRecommendCoursePlaceBinding
import com.hyosim.hamkkae.domain.model.CourseDetail
import com.hyosim.hamkkae.util.InfoConstants.INFO_HOURS
import com.hyosim.hamkkae.util.InfoConstants.INFO_LOCATION
import com.hyosim.hamkkae.util.InfoConstants.INFO_PARKING
import com.hyosim.hamkkae.util.InfoConstants.INFO_PHONE
import com.hyosim.hamkkae.util.InfoConstants.INFO_PRICE
import com.hyosim.hamkkae.util.InfoConstants.INFO_WEBSITE

class RecommendDetailDetailInfoAdapter :
    ListAdapter<CourseDetail.Info, RecommendDetailDetailInfoAdapter.RecommendDetailDetailInfoViewHolder>(
        RecommendDetailDetailInfoKeywordDiffCallback
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendDetailDetailInfoViewHolder {
        val binding = ItemRecommendCoursePlaceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecommendDetailDetailInfoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecommendDetailDetailInfoViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class RecommendDetailDetailInfoViewHolder(private val binding: ItemRecommendCoursePlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(info: CourseDetail.Info) {
            with(binding) {
                tvTime.visibility = View.GONE
                ivIcon.load(
                    when (info.type) {
                        INFO_LOCATION -> R.drawable.ic_location_gray_24
                        INFO_PHONE -> R.drawable.ic_phone_gray_24
                        INFO_PRICE -> R.drawable.ic_price_gray_24
                        INFO_HOURS -> R.drawable.ic_time_gray_24
                        INFO_WEBSITE -> R.drawable.ic_website_gray_24
                        INFO_PARKING -> R.drawable.ic_parking_gray_24
                        else -> R.drawable.ic_description_gray_24
                    }
                )
                tvPlace.text = info.content
            }
        }
    }
}

object RecommendDetailDetailInfoKeywordDiffCallback : DiffUtil.ItemCallback<CourseDetail.Info>() {
    override fun areItemsTheSame(oldItem: CourseDetail.Info, newItem: CourseDetail.Info): Boolean {
        return oldItem == newItem // id로 비교 (식별자)
    }

    override fun areContentsTheSame(
        oldItem: CourseDetail.Info,
        newItem: CourseDetail.Info
    ): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}
