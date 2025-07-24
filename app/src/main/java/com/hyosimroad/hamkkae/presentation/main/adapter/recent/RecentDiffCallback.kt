package com.hyosimroad.hamkkae.presentation.main.adapter.recent

import androidx.recyclerview.widget.DiffUtil
import com.hyosimroad.hamkkae.domain.model.Recent

object RecentDiffCallback : DiffUtil.ItemCallback<Recent>() {
    override fun areItemsTheSame(oldItem: Recent, newItem: Recent): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: Recent, newItem: Recent): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}