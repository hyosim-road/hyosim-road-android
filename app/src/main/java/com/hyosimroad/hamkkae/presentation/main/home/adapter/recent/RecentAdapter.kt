package com.hyosimroad.hamkkae.presentation.main.home.adapter.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ItemRecentActivityBinding
import com.hyosimroad.hamkkae.domain.model.Recent
import com.hyosimroad.hamkkae.util.RecentConstants.ANSWER_COMPLETE
import com.hyosimroad.hamkkae.util.RecentConstants.PHOTO_UPLOAD
import com.hyosimroad.hamkkae.util.RecentConstants.TRIP_COMPLETE
import com.hyosimroad.hamkkae.util.RecentConstants.TRIP_START

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