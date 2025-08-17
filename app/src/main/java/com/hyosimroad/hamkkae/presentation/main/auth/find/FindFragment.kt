package com.hyosimroad.hamkkae.presentation.main.auth.find

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.FragmentFindBinding
import timber.log.Timber

class FindFragment : Fragment() {
    private var _binding: FragmentFindBinding? = null
    private val binding: FragmentFindBinding
        get() = requireNotNull(_binding) { "Find fragment is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("FingFragment started!")

        setupTab()
        applySpannableStyle(
            textView = binding.tvToLogin,
            fullTextResId = R.string.find_to_login,
            targetText = "로그인"
        )

        clickLoginButton()
    }

    private fun setupTab() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> replaceFragment(FindIdFragment())
                    1 -> replaceFragment(FindPwFragment())
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.find_id_button)))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.find_pw_button)))
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
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
            val color = ContextCompat.getColor(requireContext(), R.color.auth_box_orange)

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

    private fun clickLoginButton() {
        binding.tvToLogin.setOnClickListener {
            findNavController().navigate(
                R.id.action_findFragment_to_loginFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.findFragment, true)
                    .build()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}