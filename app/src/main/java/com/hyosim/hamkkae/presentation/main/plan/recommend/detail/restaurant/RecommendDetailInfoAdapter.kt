package com.hyosim.hamkkae.presentation.main.plan.recommend.detail.restaurant

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ItemRecommendDetailInfoBinding
import com.hyosim.hamkkae.databinding.ItemTextBinding
import com.hyosim.hamkkae.domain.model.Info
import java.text.SimpleDateFormat
import java.util.Locale

class RecommendDetailInfoAdapter(
    private val category: String,
    private val clickMap: (String) -> Unit
) : ListAdapter<Info, RecommendDetailInfoAdapter.RecommendDetailInfoViewHolder>(
        RecommendDetailInfoDiffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendDetailInfoViewHolder {
        val binding = ItemRecommendDetailInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecommendDetailInfoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecommendDetailInfoViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class RecommendDetailInfoViewHolder(private val binding: ItemRecommendDetailInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(info: Info) {
            with(binding) {
                ivImage.load(R.drawable.image_trip)

                //tvType.text = info.type
                tvName.text = info.name
                tvCost.text = info.cost
                tvLocation.text = info.location
                tvDescription.text = info.description

                val additionAdapter = ItemTextAdapter(info.additionList, "addition")
                val optionAdapter = ItemTextAdapter(info.optionList, "option")

                rvAddition.layoutManager = createFlexboxLayoutManager(binding.root.context)

                rvOption.layoutManager = createFlexboxLayoutManager(binding.root.context)

                rvAddition.adapter = additionAdapter
                rvOption.adapter = optionAdapter

                if (category == "lodgings") {
                    clCheckin.visibility = View.VISIBLE
                    clCheckout.visibility = View.VISIBLE

                    tvCheckin.text = formatTime(info.checkin)
                    tvCheckout.text = formatTime(info.checkout)

                    val params = tvAddition.layoutParams as ConstraintLayout.LayoutParams
                    params.topToBottom = R.id.cl_checkin
                    tvAddition.layoutParams = params
                    binding.tvAddition.visibility = View.GONE
                }

                btnMap.setOnClickListener {
                    clickMap(info.name)
                }
            }
        }

        private fun formatTime(time: String?): String {
            return try {
                if (time.isNullOrBlank()) return ""
                val inputFormat = SimpleDateFormat("HH:mm:ss", Locale.KOREA)
                val outputFormat = SimpleDateFormat("HH:mm", Locale.KOREA)
                val date = inputFormat.parse(time)
                date?.let { outputFormat.format(it) } ?: ""
            } catch (e: Exception) {
                ""
            }
        }

        private fun createFlexboxLayoutManager(context: Context): FlexboxLayoutManager {
            return FlexboxLayoutManager(context).apply {
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
                justifyContent = JustifyContent.FLEX_START
                isAutoMeasureEnabled = true
            }
        }

        inner class ItemTextAdapter(
            private val items: List<String>,
            private val type: String // "addition" or "option"
        ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            private var isExpanded = false

            private val TYPE_ITEM = 0
            private val TYPE_TOGGLE = 1

            inner class TextViewHolder(private val binding: ItemTextBinding) :
                RecyclerView.ViewHolder(binding.root) {
                fun bind(text: String) {
                    binding.tvText.text = text
                    if (type == "addition") {
                        binding.tvText.setTypeface(null, Typeface.BOLD)
                        binding.tvText.setTextColor(binding.root.context.getColor(R.color.black))
                    } else {
                        binding.tvText.setTypeface(null, Typeface.NORMAL)
                        binding.tvText.setTextColor(binding.root.context.getColor(R.color.text_secondary_gray))
                    }
                }
            }

            inner class ToggleViewHolder(private val binding: ItemTextBinding) :
                RecyclerView.ViewHolder(binding.root) {
                fun bind() {
                    val remaining = items.size - 2
                    binding.tvText.text = if (isExpanded) "접기" else "+ ${remaining}개"
                    binding.tvText.setTypeface(null, Typeface.NORMAL)
                    binding.tvText.setTextColor(binding.root.context.getColor(R.color.text_secondary_gray))

                    binding.root.setOnClickListener {
                        isExpanded = !isExpanded
                        notifyDataSetChanged()
                    }
                }
            }

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemTextBinding.inflate(inflater, parent, false)
                return if (viewType == TYPE_ITEM) TextViewHolder(binding)
                else ToggleViewHolder(binding)
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                if (holder is TextViewHolder) {
                    holder.bind(items[position])
                } else if (holder is ToggleViewHolder) {
                    holder.bind()
                }
            }

            override fun getItemCount(): Int {
                return if (isExpanded) items.size + 1 else minOf(
                    2,
                    items.size
                ) + if (items.size > 2) 1 else 0
            }

            override fun getItemViewType(position: Int): Int {
                return if (!isExpanded && position == minOf(2, items.size)) TYPE_TOGGLE
                else if (isExpanded && position == items.size) TYPE_TOGGLE
                else TYPE_ITEM
            }
        }
    }
}

object RecommendDetailInfoDiffCallback : DiffUtil.ItemCallback<Info>() {
    override fun areItemsTheSame(oldItem: Info, newItem: Info): Boolean {
        return oldItem.id == newItem.id // id로 비교 (식별자)
    }

    override fun areContentsTheSame(oldItem: Info, newItem: Info): Boolean {
        return oldItem == newItem // 데이터 클래스라면 자동 equals 비교 가능
    }
}