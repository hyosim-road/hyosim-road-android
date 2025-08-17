package com.hyosimroad.hamkkae.presentation.main.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Typeface
import android.text.style.AbsoluteSizeSpan
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.FragmentLoginBinding
import com.hyosimroad.hamkkae.presentation.main.MainActivity
import timber.log.Timber

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = requireNotNull(_binding) { "login fragment is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("LoginFragment started!")

        applySpannableStyle(
            textView = binding.tvToSignup,
            fullTextResId = R.string.login_to_join,
            targetText = "회원가입"
        )

        applySpannableStyle(
            textView = binding.tvToFind,
            fullTextResId = R.string.login_to_find,
            targetText = "아이디, 비밀번호 찾기"
        )

        clickLoginButton()
        clickSignupButton()
        clickFindButton()
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
        binding.btnLogin.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun clickSignupButton() {
        binding.tvToSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupAgreeFragment)
        }
    }

    private fun clickFindButton() {
        binding.tvToFind.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_findFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}