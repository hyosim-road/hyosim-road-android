package com.hyosimroad.hamkkae.presentation.main.map

import android.graphics.Typeface
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ItemTextBinding

class MapAmenityAdapter() : ListAdapter<String, MapAmenityAdapter.VH>(object : DiffUtil.ItemCallback<String>() {
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
            binding.tvText.apply {
                this.text = text
                setTypeface(null, Typeface.BOLD)
                setBackgroundResource(R.drawable.bg_type_white_stroke)

                val paddingHorizontal = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 7f, resources.displayMetrics
                ).toInt()

                setPaddingRelative(paddingHorizontal, paddingTop, paddingHorizontal, paddingBottom)
            }
        }
    }
}