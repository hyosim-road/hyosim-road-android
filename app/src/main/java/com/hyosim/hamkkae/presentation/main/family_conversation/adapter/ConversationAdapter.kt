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
import com.hyosim.hamkkae.data.response_dto.conversation.GetAnswersResponseData
import com.hyosim.hamkkae.databinding.ItemAnswerBinding
import com.hyosim.hamkkae.databinding.ItemConversationAnswerBinding
import com.hyosim.hamkkae.databinding.ItemTextBinding
import com.hyosim.hamkkae.domain.model.Answer
import com.hyosim.hamkkae.presentation.main.home.noWordBreak

class ConversationAdapter :
    ListAdapter<GetAnswersResponseData, ConversationAdapter.ConversationViewHolder>(
        FamilyConversationDiffCallback
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConversationViewHolder {
        val binding =
            ItemConversationAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConversationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ConversationViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class ConversationViewHolder(private val binding: ItemConversationAnswerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(answer: GetAnswersResponseData) {
            with(binding) {

                tvQuestion.text = answer.questionContent
                tvDescription.apply {
                    text = answer.answerContent!!.noWordBreak()
                    visibility = View.VISIBLE

                    val params = layoutParams as? ViewGroup.MarginLayoutParams
                    params?.topMargin = 8.dpToPx(context)
                    layoutParams = params
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

                val modifyList = answer.tags.map{binding.root.context.getString(R.string.photo_album_tag, it)}
                textAdapter.submitList(modifyList)
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

private object FamilyConversationDiffCallback : DiffUtil.ItemCallback<GetAnswersResponseData>() {
    override fun areItemsTheSame(a: GetAnswersResponseData, b: GetAnswersResponseData) = a.questionId == b.questionId
    override fun areContentsTheSame(a: GetAnswersResponseData, b: GetAnswersResponseData) = a == b
}