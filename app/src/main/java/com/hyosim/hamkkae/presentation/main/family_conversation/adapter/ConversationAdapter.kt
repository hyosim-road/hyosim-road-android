package com.hyosim.hamkkae.presentation.main.family_conversation.adapter

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ItemAnswerBinding
import com.hyosim.hamkkae.databinding.ItemTextBinding
import com.hyosim.hamkkae.domain.model.Answer

class ConversationAdapter :
    ListAdapter<Answer, ConversationAdapter.ConversationViewHolder>(
        FamilyConversationDiffCallback
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConversationViewHolder {
        val binding =
            ItemAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConversationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ConversationViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class ConversationViewHolder(private val binding: ItemAnswerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(answer: Answer) {
            with(binding) {
                ivIcon.visibility = View.GONE
                tvNumber.visibility = View.GONE

                tvPlace.text = answer.place
                tvDescription.apply {
                    text = answer.content
                    visibility = View.VISIBLE

                    val params = layoutParams as? ViewGroup.MarginLayoutParams
                    params?.topMargin = 8.dpToPx(context)
                    layoutParams = params

                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 17f)
                }

                val textAdapter = TextAdapter()
                val flexboxLayoutManager = FlexboxLayoutManager(binding.root.context).apply {
                    flexDirection = FlexDirection.ROW
                    flexWrap = FlexWrap.WRAP
                    justifyContent = JustifyContent.FLEX_START
                    isAutoMeasureEnabled = true
                }

                binding.rvKeywords.layoutManager = flexboxLayoutManager
                rvKeywords.adapter = textAdapter

                val modifyList = answer.keywords.map{binding.root.context.getString(R.string.photo_album_tag, it)}
                textAdapter.submitList(modifyList)

                val cardView =
                    binding.root as CardView // binding.root가 CardView인 경우, 또는 binding.cvTripSchedule

                cardView.cardElevation = 2.dpToPx(cardView.context).toFloat()
                cardView.radius =
                    10.dpToPx(cardView.context).toFloat() // cardCornerRadius는 radius 프로퍼티로 접근

                val layoutParams = cardView.layoutParams as? ViewGroup.MarginLayoutParams
                layoutParams?.let {
                    it.topMargin = 5.dpToPx(cardView.context)
                    it.bottomMargin = 5.dpToPx(cardView.context)
                    it.marginStart = 3.dpToPx(cardView.context)
                    it.marginEnd = 3.dpToPx(cardView.context)
                    cardView.layoutParams = it
                }

                val parentLayout = root as? ConstraintLayout // root가 해당 ConstraintLayout이라고 가정
                parentLayout?.let { layout ->
                    val constraintSet = ConstraintSet()
                    constraintSet.clone(layout) // 현재 레이아웃의 제약 조건 복제

                    // tvPlace의 하단을 tvDescription의 상단에 연결
                    constraintSet.connect(
                        tvPlace.id,
                        ConstraintSet.BOTTOM,
                        tvDescription.id,
                        ConstraintSet.TOP,
                    )

                    // tvDescription의 하단을 rvKeywords의 하단에 연결
                    constraintSet.connect(
                        tvDescription.id, // tvDescription의 ID
                        ConstraintSet.BOTTOM, // tvDescription의 하단 앵커
                        rvKeywords.id, // rvKeywords의 ID
                        ConstraintSet.BOTTOM, // 부모의 하단 앵커
                    )
                    // (필요하다면 다른 제약 조건도 여기서 설정/수정)
                    constraintSet.clear(binding.ivIcon.id, ConstraintSet.TOP)

                    constraintSet.applyTo(layout) // 변경된 제약 조건 적용
                }

                rvKeywords.visibility = View.VISIBLE
            }
        }
    }

    class TextAdapter() : ListAdapter<String, TextAdapter.VH>(object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(o: String, n: String) = o == n
        override fun areContentsTheSame(o: String, n: String) = o == n
    }) {

        private var isExpanded: Boolean = false // State variable

        companion object {
            private const val VIEW_TYPE_KEYWORD = 0
            private const val VIEW_TYPE_MORE = 1
            private const val VIEW_TYPE_FOLD = 2
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val binding = ItemTextBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return VH(binding)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            if (isExpanded) {
                // 1. 펼쳐진 상태
                if (position < currentList.size) {
                    // 모든 키워드 표시
                    holder.bind(getItem(position))
                } else {
                    // 마지막 아이템에 '접기' 버튼 표시
                    holder.bindFoldButton()
                    // '접기' 버튼 클릭 리스너 설정
                    holder.itemView.setOnClickListener {
                        isExpanded = false
                        notifyDataSetChanged() // 어댑터 갱신
                    }
                }
            } else {
                // 2. 접힌 상태
                if (position < 2) {
                    // 첫 2개 키워드 표시
                    holder.bind(getItem(position))
                } else {
                    // 3번째 아이템에 '+n개 더' 버튼 표시
                    val remainingCount = currentList.size - 2
                    holder.bindMoreButton(remainingCount)
                    // '+n개 더' 버튼 클릭 리스너 설정
                    holder.itemView.setOnClickListener {
                        isExpanded = true
                        notifyDataSetChanged() // 어댑터 갱신
                    }
                }
            }
        }

        override fun getItemCount(): Int {
            return if (isExpanded) {
                currentList.size + 1 // all keywords + fold button
            } else {
                if (currentList.size > 2) {
                    3 // 2 keywords + "+ n개 더"
                } else {
                    currentList.size
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            return if (isExpanded) {
                if (position == currentList.size) { // The last item is the fold button
                    VIEW_TYPE_FOLD
                } else {
                    VIEW_TYPE_KEYWORD
                }
            } else {
                if (position == 2 && currentList.size > 2) { // The third item is the "+n more" button
                    VIEW_TYPE_MORE
                } else {
                    VIEW_TYPE_KEYWORD
                }
            }
        }

        inner class VH(private val binding: ItemTextBinding) :
            RecyclerView.ViewHolder(binding.root) {

            // 일반 키워드 바인딩 함수
            fun bind(text: String) {
                with(binding.tvText) {
                    this.text = text
                    // 키워드 태그 배경색 (예: R.drawable.bg_white_stroke)
                    setTextColor(binding.root.context.getColor(R.color.primary_orange))
                    setTypeface(typeface, Typeface.BOLD)
                }
            }

            // '+n개 더' 버튼 바인딩 함수
            fun bindMoreButton(remainingCount: Int) {
                with(binding.tvText) {
                    this.text = binding.root.context.getString(R.string.answers_more, remainingCount)
                    setTextColor(binding.root.context.getColor(R.color.text_secondary_gray))
                }
            }

            // '접기' 버튼 바인딩 함수
            fun bindFoldButton() {
                with(binding.tvText) {
                    this.text = "접기"
                    setTextColor(binding.root.context.getColor(R.color.text_secondary_gray))
                }
            }
        }
    }
    // dp 값을 픽셀로 변환하는 확장 함수 (확장 함수를 정의해야 함)
    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }
}

private object FamilyConversationDiffCallback : DiffUtil.ItemCallback<Answer>() {
    override fun areItemsTheSame(a: Answer, b: Answer) = a.id == b.id
    override fun areContentsTheSame(a: Answer, b: Answer) = a == b
}