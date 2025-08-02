package com.hyosimroad.hamkkae.presentation.main.plan.recommend.detail.tip

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ItemTextBinding

class TextAdapter(
    private val mode:TextMode = TextMode.NORMAL
) : ListAdapter<String, TextAdapter.VH>(object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(o: String, n: String) = o == n
    override fun areContentsTheSame(o: String, n: String) = o == n
}) {

    inner class VH(private val binding: ItemTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            val ctx = binding.root.context
            val styled = when (mode) {
                TextMode.NORMAL -> text
                TextMode.PREFIX_BOLD -> text.boldPrefixBeforeColon()
                TextMode.FULL_BOLD -> text.asBold()                // ✅ 전체 Bold
            }

            val bullet = ctx.getString(R.string.tip_bullet) // "• "
            binding.tvText.text = SpannableStringBuilder()
                .append(bullet)
                .append(styled)   // ← span 유지됨
        }

        /** "봄(4~5월): 벚꽃…" → "봄(4~5월):" 만 Bold(+선택: 컬러) */
        fun CharSequence.boldPrefixBeforeColon(
            context: Context? = null,
            colorRes: Int? = null
        ): CharSequence {
            val text = this.toString()
            val end = text.indexOf(':').let { if (it >= 0) it + 1 else return text } // 콜론 포함까지
            return SpannableString(text).apply {
                setSpan(StyleSpan(Typeface.BOLD), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                colorRes?.let {
                    val c = ContextCompat.getColor(context!!, it)
                    setSpan(ForegroundColorSpan(c), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
        }

        fun CharSequence.asBold(): CharSequence =
            SpannableString(this).apply {
                setSpan(StyleSpan(Typeface.BOLD), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemTextBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}

enum class TextMode{NORMAL, PREFIX_BOLD, FULL_BOLD}