package com.hyosim.hamkkae.presentation.main.trip_detail.album

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ItemTextBinding

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
        holder.bind(getItem(position))
    }

    inner class VH(private val binding: ItemTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            val bullet = binding.root.context.getString(R.string.tip_bullet)
            binding.tvText.text = SpannableStringBuilder()
                .append(bullet)
                .append(text)
        }
    }
}