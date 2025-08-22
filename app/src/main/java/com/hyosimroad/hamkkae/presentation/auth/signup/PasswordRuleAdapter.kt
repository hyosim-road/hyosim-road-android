package com.hyosimroad.hamkkae.presentation.auth.signup

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.ItemPwRuleBinding
import com.hyosimroad.hamkkae.databinding.ItemTextBinding
import com.hyosimroad.hamkkae.domain.model.PasswordRule
import com.hyosimroad.hamkkae.domain.model.TripRecord
import com.hyosimroad.hamkkae.presentation.main.photo_album.TextAdapter

class PasswordRuleAdapter: ListAdapter<PasswordRule, PasswordRuleAdapter.PasswordRuleViewHolder>(object : DiffUtil.ItemCallback<PasswordRule>() {
    override fun areItemsTheSame(o: PasswordRule, n: PasswordRule) = o == n
    override fun areContentsTheSame(o: PasswordRule, n: PasswordRule) = o == n
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordRuleViewHolder {
        val binding = ItemPwRuleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PasswordRuleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PasswordRuleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PasswordRuleViewHolder(private val binding: ItemPwRuleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rule: PasswordRule) {
            with(binding){
                val context = binding.root.context
                val green = ContextCompat.getColor(context, R.color.auth_check_green)
                val gray = ContextCompat.getColor(context, R.color.text_secondary_gray)

                with(binding) {
                    // 조건 만족 여부에 따라 아이콘/색상 변경
                    if (rule.isSatisfied) {
                        ivRuleIcon.setImageResource(R.drawable.ic_check_green_24) // ✅ 체크 아이콘
                        ImageViewCompat.setImageTintList(ivRuleIcon, ColorStateList.valueOf(green))
                        tvRuleText.setTextColor(green)
                    } else {
                        ivRuleIcon.setImageResource(R.drawable.ic_no_white_24) // ❌ 엑스 아이콘
                        ImageViewCompat.setImageTintList(ivRuleIcon, ColorStateList.valueOf(gray))
                        tvRuleText.setTextColor(gray)
                    }

                    tvRuleText.text = rule.text
                }
            }
        }
    }
}