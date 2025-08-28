package com.hyosim.hamkkae.presentation.main.trip_records.detail.overview

import android.graphics.Typeface
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosim.hamkkae.databinding.ItemTextBinding

class OverviewTextAdapter() :
    ListAdapter<String, OverviewTextAdapter.VH>(object : DiffUtil.ItemCallback<String>() {
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
        holder.bind(getItem(position))
    }

    inner class VH(private val binding: ItemTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            with(binding.tvText) {
                this.text = text

                // Bold 처리
                setTypeface(typeface, Typeface.BOLD)

                // textSize 16sp
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            }

        }
    }
}
