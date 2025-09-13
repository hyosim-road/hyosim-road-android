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

    private var selectedItem: TripStyle? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectTripStyleViewHolder {
        val binding = ItemTripStyleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectTripStyleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectTripStyleViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, item == selectedItem)
    }

    fun selectItem(item: TripStyle?) {
        val previousSelected = selectedItem
        selectedItem = item

        // 이전 선택된 아이템 위치만 갱신
        previousSelected?.let {
            val prevIndex = currentList.indexOf(it)
            if (prevIndex != -1) notifyItemChanged(prevIndex)
        }

        // 새로 선택된 아이템 위치 갱신
        val newIndex = currentList.indexOf(item)
        if (newIndex != -1) notifyItemChanged(newIndex)
    }

    inner class SelectTripStyleViewHolder(
        private val binding: ItemTripStyleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.clTripStyle.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val clickedItem = getItem(adapterPosition)
                    selectItem(clickedItem)   // ✅ 선택 갱신
                    onItemClick(clickedItem)  // ✅ 외부로 선택 이벤트 전달
                }
            }
        }

        fun bind(item: TripStyle, isSelected: Boolean) {
            binding.ivIcon.setImageResource(item.icon)
            binding.tvLabel.text = item.name

            binding.clTripStyle.setBackgroundResource(
                if (isSelected) R.drawable.bg_selected_style_box
                else R.drawable.bg_select_style_box
            )
        }
    }
}

private object TripStyleDiffCallback : DiffUtil.ItemCallback<TripStyle>() {
    override fun areItemsTheSame(a: TripStyle, b: TripStyle) = a.id == b.id
    override fun areContentsTheSame(a: TripStyle, b: TripStyle) = a == b
}