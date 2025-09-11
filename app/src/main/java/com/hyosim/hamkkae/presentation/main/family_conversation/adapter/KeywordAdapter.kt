package com.hyosim.hamkkae.presentation.main.family_conversation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosim.hamkkae.databinding.ItemRecommendCourseKeywordBinding

class KeywordAdapter : ListAdapter<String, KeywordAdapter.KeywordViewHolder>(KeywordDiffCallback) {

    inner class KeywordViewHolder(private val binding: ItemRecommendCourseKeywordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(keyword: String) {
            binding.tvKeyword.text = keyword
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        val binding = ItemRecommendCourseKeywordBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return KeywordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        val keyword = getItem(position)
        holder.bind(keyword)
    }
}

private object KeywordDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
