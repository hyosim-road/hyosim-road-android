package com.hyosimroad.hamkkae.presentation.main.plan.select.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosimroad.hamkkae.databinding.ItemTripStyleBinding
import com.hyosimroad.hamkkae.domain.model.TripStyle

class SelectTripStyleAdapter(
    private val clickItem: (TripStyle, Boolean) -> Unit
) :
    ListAdapter<TripStyle, SelectTripStyleAdapter.SelectTripStyleViewHolder>(
        TripStyleDiffCallback
    ) {
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectTripStyleViewHolder {
        val binding =
            ItemTripStyleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectTripStyleViewHolder(binding, viewPool)
    }

    override fun onBindViewHolder(
        holder: SelectTripStyleViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class SelectTripStyleViewHolder(
        val binding: ItemTripStyleBinding,
        private val viewPool: RecyclerView.RecycledViewPool
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.clTripStyle.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos == RecyclerView.NO_POSITION) return@setOnClickListener
                val item = getItem(pos)
                val willSelect = !selectedIds.contains(item.id)
                clickItem(item, willSelect)
            }
        }

        fun bind(item: TripStyle) = with(binding) {
            ivIcon.setImageResource(item.icon)
            tvLabel.text = item.name
        }
    }
}

private object TripStyleDiffCallback : DiffUtil.ItemCallback<TripStyle>() {
    override fun areItemsTheSame(a: TripStyle, b: TripStyle) = a.id == b.id
    override fun areContentsTheSame(a: TripStyle, b: TripStyle) = a == b
}