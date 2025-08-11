package com.hyosimroad.hamkkae.presentation.main.trip_records.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ItemTextBinding

class TextAdapter() : ListAdapter<String, TextAdapter.VH>(object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(o: String, n: String) = o == n
    override fun areContentsTheSame(o: String, n: String) = o == n
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemTextBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (position < 3) {
            holder.bind(getItem(position))
        } else if (position == 3) {
            val remainingCount = currentList.size - 3
            holder.bind("+$remainingCount")
        }
    }

    override fun getItemCount(): Int {
        return if (currentList.size > 3) {
            4 // 3개 항목 + 1개 (+n) 항목
        } else {
            currentList.size
        }
    }

    inner class VH(private val binding: ItemTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            with(binding.tvText){
                binding.tvText.text = text
                binding.tvText.setBackgroundResource(R.drawable.bg_white_stroke)

                val paddingPx = 8.dpToPx(binding.root.context)
                binding.tvText.setPadding(paddingPx, 0, paddingPx, 0) // 좌우 패딩만 적용
            }
        }

        // dp 값을 픽셀로 변환하는 확장 함수 (확장 함수를 정의해야 함)
        fun Int.dpToPx(context: Context): Int {
            return (this * context.resources.displayMetrics.density).toInt()
        }
    }
}