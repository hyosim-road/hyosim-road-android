package com.hyosim.hamkkae.presentation.main.plan.recommend.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ItemRecommendCourseKeywordBinding
import com.hyosim.hamkkae.util.KeywordConstants.KEYWORD_CULTURE
import com.hyosim.hamkkae.util.KeywordConstants.KEYWORD_HISTORY
import com.hyosim.hamkkae.util.KeywordConstants.KEYWORD_TEMPLE

class RecommendCourseKeywordAdapter :
    ListAdapter<String, RecommendCourseKeywordAdapter.RecommendCourseKeywordViewHodler>(RecommendCourseKeywordDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendCourseKeywordViewHodler {
        val binding = ItemRecommendCourseKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendCourseKeywordViewHodler(binding)
    }

    override fun onBindViewHolder(
        holder: RecommendCourseKeywordViewHodler,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class RecommendCourseKeywordViewHodler(private val binding: ItemRecommendCourseKeywordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(keyword:String){
            with(binding){
                tvKeyword.text = keyword

                when(keyword){
                    KEYWORD_HISTORY -> ivIcon.setImageResource(R.drawable.ic_history)
                    KEYWORD_TEMPLE -> ivIcon.setImageResource(R.drawable.ic_temple)
                    KEYWORD_CULTURE -> ivIcon.setImageResource(R.drawable.ic_culture)
                }
            }
        }
    }
}

object RecommendCourseKeywordDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}

