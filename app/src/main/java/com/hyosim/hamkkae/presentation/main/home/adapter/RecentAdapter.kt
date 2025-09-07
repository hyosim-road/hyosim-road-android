package com.hyosim.hamkkae.presentation.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ItemRecentActivityBinding
import com.hyosim.hamkkae.domain.model.Recent
import com.hyosim.hamkkae.util.RecentConstants.ANSWER_COMPLETE
import com.hyosim.hamkkae.util.RecentConstants.PHOTO_UPLOAD
import com.hyosim.hamkkae.util.RecentConstants.TRIP_COMPLETE
import com.hyosim.hamkkae.util.RecentConstants.TRIP_START

class RecentAdapter: ListAdapter<Recent, RecentAdapter.RecentViewHolder>(RecentDiffCallback) {
    override fun getItemCount(): Int {
        return minOf(super.getItemCount(), 3) // 항상 최대 3개까지만 보여줌
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentViewHolder {
        val binding = ItemRecentActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecentViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class RecentViewHolder(private val binding: ItemRecentActivityBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(activity: Recent) {
            with(binding){
                ivIcon.load(when(activity.type){
                    TRIP_COMPLETE -> R.drawable.ic_award
                    PHOTO_UPLOAD -> R.drawable.ic_upload
                    TRIP_START -> R.drawable.ic_award
                    ANSWER_COMPLETE -> R.drawable.ic_answer
                    else -> R.drawable.ic_tip
                })
                tvContent.text = activity.content
                tvTime.text = (
                        if(activity.time<1) binding.root.context.getString(R.string.main_recent_just)
                        else{
                            if(activity.time<60) binding.root.context.getString(R.string.main_recent_minute, activity.time)
                            else binding.root.context.getString(R.string.main_recent_hour, activity.time/60)
                        })
            }
        }
    }
}

object RecentDiffCallback : DiffUtil.ItemCallback<Recent>() {
    override fun areItemsTheSame(oldItem: Recent, newItem: Recent): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: Recent, newItem: Recent): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}