package com.hyosimroad.hamkkae.presentation.main.auth.signup

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
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.FragmentSignupInfoBinding
import timber.log.Timber

class SignupInfoFragment : Fragment() {
    private var _binding: FragmentSignupInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("SignupIngoFragment started!")

        applySpannableStyle(
            textView = binding.tvToLogin,
            fullTextResId = R.string.signup_to_login,
            targetText = "로그인"
        )

        clickSignupButton()
        clickLoginButton()
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

    private fun checkId() {

    }

    private fun checkPw() {

    }

    private fun checkEmail() {

    }

    private fun checkCode() {

    }

    private fun clickSignupButton() {
        binding.btnSignup.setOnClickListener {
            findNavController().navigate(
                R.id.action_signupInfoFragment_to_loginFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.signupInfoFragment, true)
                    .setPopUpTo(R.id.signupAgreeFragment, true)
                    .build()
            )
        }
    }

    private fun clickLoginButton() {
        binding.tvToLogin.setOnClickListener {
            findNavController().navigate(
                R.id.action_signupInfoFragment_to_loginFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.signupInfoFragment, true)
                    .setPopUpTo(R.id.signupAgreeFragment, true)
                    .build()
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}