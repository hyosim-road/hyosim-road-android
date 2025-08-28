package com.hyosim.hamkkae.presentation.main.plan.select.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ItemTripStyleBinding
import com.hyosim.hamkkae.domain.model.TripStyle

class SelectTripStyleAdapter(
    private val onItemClick: (TripStyle) -> Unit
) : ListAdapter<TripStyle, SelectTripStyleAdapter.SelectTripStyleViewHolder>(TripStyleDiffCallback) {

    private var selectedItems: List<TripStyle> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectTripStyleViewHolder {
        val binding = ItemTripStyleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectTripStyleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectTripStyleViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, selectedItems.contains(item))
    }

    fun submitSelection(items: List<TripStyle>) {
        selectedItems = items
        notifyDataSetChanged()
    }

    inner class SelectTripStyleViewHolder(
        private val binding: ItemTripStyleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.clTripStyle.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(adapterPosition))
                }
            }
        }

        fun bind(item: TripStyle, isSelected: Boolean) {
            binding.ivIcon.setImageResource(item.icon)
            binding.tvLabel.text = item.name

            if (isSelected) {
                binding.clTripStyle.setBackgroundResource(R.drawable.bg_selected_style_box)
            } else {
                binding.clTripStyle.setBackgroundResource(R.drawable.bg_select_style_box)
            }
        }
    }
}

private object TripStyleDiffCallback : DiffUtil.ItemCallback<TripStyle>() {
    override fun areItemsTheSame(a: TripStyle, b: TripStyle) = a.id == b.id
    override fun areContentsTheSame(a: TripStyle, b: TripStyle) = a == b
}