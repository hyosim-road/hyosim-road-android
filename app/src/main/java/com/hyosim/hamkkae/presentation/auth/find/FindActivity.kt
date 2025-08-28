package com.hyosim.hamkkae.presentation.auth.find

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.tabs.TabLayout
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.ActivityFindBinding
import com.hyosim.hamkkae.presentation.auth.find.id.FindIdFragment
import com.hyosim.hamkkae.presentation.auth.find.pw.FindPwFragment
import com.hyosim.hamkkae.presentation.main.setting.user.ChangePwNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindActivity : AppCompatActivity(), ChangePwNavigator {
    private lateinit var binding: ActivityFindBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setting()
    }

    private fun initBinds() {
        binding = ActivityFindBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting(){
        setupTab()
        applySpannableStyle(
            textView = binding.tvToLogin,
            fullTextResId = R.string.find_to_login,
            targetText = "로그인"
        )
    }

    private fun setupTab() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> replaceFragment(FindIdFragment(), null)
                    1 -> replaceFragment(FindPwFragment(), null)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.find_id_button)))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.find_pw_button)))
    }

    fun replaceFragment(fragment: Fragment, email: String?) {
        val bundle = Bundle()
        if (!email.isNullOrEmpty()) {
            bundle.putString("email", email)
            fragment.arguments = bundle
        }

        supportFragmentManager.commit {
            replace(R.id.fcv_find, fragment)
            setReorderingAllowed(true)
        }
    }

    private fun applySpannableStyle(
        textView: TextView,
        @StringRes fullTextResId: Int,
        targetText: String
    ) {
        val fullText = getString(fullTextResId)
        val spannableString = SpannableString(fullText)
        val startIndex = fullText.indexOf(targetText)

        if (startIndex != -1) {
            val endIndex = startIndex + targetText.length
            val color = ContextCompat.getColor(this, R.color.auth_box_orange)

            val sizeInPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                16f,
                resources.displayMetrics
            ).toInt()

            spannableString.setSpan(StyleSpan(Typeface.BOLD_ITALIC), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(UnderlineSpan(), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(ForegroundColorSpan(color), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(AbsoluteSizeSpan(sizeInPx), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        textView.text = spannableString
    }

    fun selectTab(index: Int) {
        binding.tabLayout.getTabAt(index)?.select()
    }

    override fun onPasswordChanged() {
        // supportFragmentManager 기반 이동
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_find, FindPwFragment())
            .commit()
    }
}