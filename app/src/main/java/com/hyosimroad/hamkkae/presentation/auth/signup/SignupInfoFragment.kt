package com.hyosimroad.hamkkae.presentation.auth.signup

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
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
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.FragmentSignupInfoBinding
import com.hyosimroad.hamkkae.domain.model.PasswordRule
import com.hyosimroad.hamkkae.extension.auth.CheckIdState
import com.hyosimroad.hamkkae.extension.auth.CodeState
import com.hyosimroad.hamkkae.extension.auth.SendEmailState
import com.hyosimroad.hamkkae.extension.auth.SignUpState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SignupInfoFragment : Fragment() {
    private var _binding: FragmentSignupInfoBinding? = null
    private val binding get() = _binding!!
    private val signupInfoViewModel: SignupInfoViewModel by viewModels()
    private val redColor by lazy {
        ContextCompat.getColor(requireContext(), R.color.auth_no_red)
    }
    private val greenColor by lazy {
        ContextCompat.getColor(requireContext(), R.color.auth_check_green)
    }

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
        setting()

    }

    private fun setting() {
        applySpannableStyle(
            textView = binding.tvToLogin,
            fullTextResId = R.string.signup_to_login,
            targetText = "로그인"
        )

        binding.etSignupId.addTextChangedListener {
            checkId()
        }
        binding.etSignupPw.addTextChangedListener {
            checkPw(it.toString())
        }
        checkEmail()
        binding.etSignupCode.addTextChangedListener {
            binding.btnCheckEmailCode.isEnabled = true
            binding.btnCheckEmailCode.backgroundTintList =
                ColorStateList.valueOf(requireContext().getColor(R.color.auth_box_orange))
        }

        isIdAvailable()
        validatePasswordMatch()
        sendEmail()
        checkCode()

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

            spannableString.setSpan(
                StyleSpan(Typeface.BOLD_ITALIC),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                UnderlineSpan(),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(color),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                AbsoluteSizeSpan(sizeInPx),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        textView.text = spannableString
    }

    private fun checkId() {
        val id = binding.etSignupId.text.toString().trim()
        val regex = "^[a-zA-Z0-9]{6,12}$".toRegex()

        if (!id.matches(regex)) {
            with(binding) {
                tilSignupId.setBoxStrokeColor(requireContext().getColor(R.color.auth_no_red))

                tvAvailableId.apply {
                    visibility = View.VISIBLE
                    text = getString(R.string.signup_info_id_rule)
                    setTextColor(requireContext().getColor(R.color.auth_no_red))
                }

                icId.visibility = View.VISIBLE
                icId.setImageResource(R.drawable.ic_no_white_24)  // 아이콘 설정
                ImageViewCompat.setImageTintList(
                    icId,
                    ColorStateList.valueOf(redColor)
                )  // 빨간색 Tint 적용

                btnCheckId.isEnabled = true
                btnCheckId.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.auth_gray))
            }
        } else {
            with(binding) {
                tilSignupId.setBoxStrokeColor(
                    requireContext().getColor(R.color.auth_check_green)
                )

                tvAvailableId.visibility = View.GONE
                icId.visibility = View.GONE

                btnCheckId.isEnabled = true
                btnCheckId.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.auth_box_orange))
            }

        }
    }

    private fun checkPw(password: String) {
        val passwordRuleAdapter = PasswordRuleAdapter()
        val rules = listOf(
            PasswordRule("8자 이상", password.length >= 8),
            PasswordRule("영문 대문자", password.any { it.isUpperCase() }),
            PasswordRule("영문 소문자", password.any { it.isLowerCase() }),
            PasswordRule("숫자", password.any { it.isDigit() }),
            PasswordRule("특수문자", password.any { !it.isLetterOrDigit() })
        )
        binding.rvPwRule.adapter = passwordRuleAdapter
        passwordRuleAdapter.submitList(rules)

        val gray = ContextCompat.getColor(context, R.color.text_secondary_gray)

        binding.tilSignupPw.setBoxStrokeColor(
            when {
                password.isEmpty() -> gray                // 입력 없음
                rules.all { it.isSatisfied } -> greenColor     // 모두 충족
                else -> redColor                               // 일부만 충족
            })
    }

    private fun validatePasswordMatch() {
        with(binding) {
            etSignupPwCheck.addTextChangedListener {
                tvSamePw.visibility = View.VISIBLE
                icSamePw.visibility = View.VISIBLE
                val pw = etSignupPw.text.toString()
                val pwCheck = etSignupPwCheck.text.toString()

                if (pw == pwCheck) {
                    tilSignupPwCheck.setBoxStrokeColor(requireContext().getColor(R.color.complete_preview_bg_gray))
                    tvSamePw.text = getString(R.string.signup_info_pw_match)
                    tvSamePw.setTextColor(requireContext().getColor(R.color.auth_check_green))
                    icSamePw.setImageResource(R.drawable.ic_check_green_24)
                    ImageViewCompat.setImageTintList(
                        icSamePw,
                        ColorStateList.valueOf(greenColor)
                    )
                    checkSignUpAvailable()
                } else {
                    tilSignupPwCheck.setBoxStrokeColor(requireContext().getColor(R.color.auth_no_red))
                    tvSamePw.text = getString(R.string.signup_info_pw_not_match)
                    tvSamePw.setTextColor(requireContext().getColor(R.color.auth_no_red))
                    icSamePw.setImageResource(R.drawable.ic_no_white_24)
                    ImageViewCompat.setImageTintList(
                        icSamePw,
                        ColorStateList.valueOf(redColor)
                    )
                }
            }
        }
    }

    private fun checkEmail() {
        with(binding) {
            etSignupEmail.addTextChangedListener {
                if (isValidEmail(it.toString())) {
                    tvAvailableEmail.visibility = View.GONE
                    icEmail.visibility = View.INVISIBLE

                    btnRequestEmailCode.isEnabled = true
                    btnRequestEmailCode.backgroundTintList =
                        ColorStateList.valueOf(requireContext().getColor(R.color.auth_box_orange))
                } else {
                    tvAvailableEmail.apply {
                        visibility = View.VISIBLE
                        text = getString(R.string.signup_info_email_rule)
                        setTextColor(requireContext().getColor(R.color.auth_no_red))
                    }

                    icEmail.visibility = View.VISIBLE
                    icEmail.setImageResource(R.drawable.ic_no_white_24)  // 아이콘 설정
                    ImageViewCompat.setImageTintList(
                        binding.icEmail,
                        ColorStateList.valueOf(redColor)
                    )

                    btnRequestEmailCode.isEnabled = false
                    btnRequestEmailCode.backgroundTintList =
                        ColorStateList.valueOf(requireContext().getColor(R.color.auth_gray))
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        // 영문/숫자/._%+- 조합 + @ + 도메인 + 최종 .com/.net/.org 등
        val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
        return email.matches(regex)
    }

    private fun isIdAvailable() {
        lifecycleScope.launch {
            signupInfoViewModel.checkIdState.collect { state ->
                when (state) {
                    is CheckIdState.Success -> {
                        with(binding) {
                            tvAvailableId.visibility = View.VISIBLE
                            icId.visibility = View.VISIBLE
                            if (!state.isExist) {
                                tilSignupId.setBoxStrokeColor(requireContext().getColor(R.color.auth_check_green))

                                tvAvailableId.text =
                                    requireContext().getString(R.string.signup_info_id_available)
                                tvAvailableId.setTextColor(requireContext().getColor(R.color.auth_check_green))

                                icId.setImageResource(R.drawable.ic_check_green_24)
                                ImageViewCompat.setImageTintList(
                                    icId,
                                    ColorStateList.valueOf(greenColor)
                                )

                                checkSignUpAvailable()
                            } else {
                                tilSignupId.setBoxStrokeColor(requireContext().getColor(R.color.auth_no_red))
                                tvAvailableId.text =
                                    requireContext().getString(R.string.signup_info_id_not_available)
                                tvAvailableId.setTextColor(requireContext().getColor(R.color.auth_no_red))

                                icId.setImageResource(R.drawable.ic_no_white_24)  // 아이콘 설정
                                ImageViewCompat.setImageTintList(
                                    icId,
                                    ColorStateList.valueOf(redColor)
                                )  // 빨간색 Tint 적용
                            }
                        }
                    }

                    is CheckIdState.Error -> {
                        // 인터넷 에러..
                    }

                    CheckIdState.Loading -> {

                    }
                }
            }
        }

        binding.btnCheckId.setOnClickListener {
            val id = binding.etSignupId.text.toString()
            signupInfoViewModel.checkId(id)
        }
    }

    private fun sendEmail() {
        lifecycleScope.launch {
            signupInfoViewModel.sendEmailState.collect { state ->
                when (state) {
                    is SendEmailState.Success -> {
                        setEmailButtonLoading(false)
                        with(binding) {
                            tvAvailableEmail.apply {
                                visibility = View.VISIBLE
                                setTextColor(requireContext().getColor(R.color.auth_check_green))
                            }

                            btnRequestEmailCode.text = getString(R.string.signup_info_email_resend)
                            checkSignUpAvailable()

                            // 5분 타이머 시작 (300초 = 300000ms)
                            object : CountDownTimer(5 * 60 * 1000, 1000) {
                                override fun onTick(millisUntilFinished: Long) {
                                    val minutes = (millisUntilFinished / 1000) / 60
                                    val seconds = (millisUntilFinished / 1000) % 60
                                    tvAvailableEmail.text =
                                        getString(R.string.signup_info_email_send) +
                                                " (${String.format("%02d:%02d", minutes, seconds)})"
                                }

                                override fun onFinish() {
                                    tvAvailableEmail.text =
                                        getString(R.string.signup_info_email_finished)
                                    tvAvailableEmail.setTextColor(requireContext().getColor(R.color.auth_no_red))
                                }
                            }.start()
                        }
                    }

                    is SendEmailState.Error -> {
                        // 인터넷 에러..
                    }

                    is SendEmailState.Loading -> {

                    }
                }
            }
        }

        binding.btnRequestEmailCode.setOnClickListener {
            setEmailButtonLoading(true)
            val email = binding.etSignupEmail.text.toString()
            signupInfoViewModel.sendEmail(email)
        }
    }

    private fun setEmailButtonLoading(isLoading: Boolean) {
        if (isLoading) {
            val grayColor = ContextCompat.getColor(requireContext(), R.color.auth_gray)
            binding.btnRequestEmailCode.apply {
                text = getString(R.string.signup_info_email_sending)
                isEnabled = false
                backgroundTintList = ColorStateList.valueOf(grayColor)
            }
        } else {
            val orangeColor = ContextCompat.getColor(requireContext(), R.color.auth_box_orange)
            binding.btnRequestEmailCode.isEnabled = true
            binding.btnRequestEmailCode.backgroundTintList = ColorStateList.valueOf(orangeColor)
        }
    }


    private fun checkCode() {
        lifecycleScope.launch {
            signupInfoViewModel.codeState.collect { state ->
                when (state) {
                    is CodeState.Success -> {
                        if (state.success) {
                            binding.tvAvailableCode.apply {
                                visibility = View.VISIBLE
                                text = getString(R.string.signup_info_code_match)
                                setTextColor(requireContext().getColor(R.color.auth_check_green))
                                checkSignUpAvailable()
                            }
                        } else {
                            binding.tvAvailableCode.apply {
                                visibility = View.VISIBLE
                                text = getString(R.string.signup_info_code_no_match)
                                setTextColor(requireContext().getColor(R.color.auth_no_red))
                            }
                        }
                    }

                    is CodeState.Error -> {
                        // 인터넷 오류
                    }

                    is CodeState.Loading -> {}
                }
            }
        }

        binding.btnCheckEmailCode.setOnClickListener {
            val email = binding.etSignupEmail.text.toString()
            val code = binding.etSignupCode.text.toString()
            signupInfoViewModel.verifyCode(email, code)
        }
    }

    private fun checkSignUpAvailable() {
        val idInput = binding.etSignupId.text?.toString() ?: ""
        val emailInput = binding.etSignupEmail.text?.toString() ?: ""
        val codeInput = binding.etSignupCode.text?.toString() ?: ""
        val pwInput = binding.etSignupPw.text?.toString() ?: ""
        val pwCheckInput = binding.etSignupPwCheck.text?.toString() ?: ""

        val idOk = signupInfoViewModel.verifiedId != null &&
                signupInfoViewModel.verifiedId == idInput
        val emailOk = signupInfoViewModel.verifiedEmail != null &&
                signupInfoViewModel.verifiedEmail == emailInput
        val codeOk = signupInfoViewModel.verifiedCode != null &&
                signupInfoViewModel.verifiedCode == codeInput
        val pwOk = pwInput.isNotEmpty() && pwInput == pwCheckInput

        val allOk = idOk && emailOk && codeOk && pwOk

        Timber.d("idInput: ${idInput}, emailInput: ${emailInput}, codeInput: ${codeInput}, pwInput: ${pwInput}, pwCheckInput: ${pwCheckInput}")
        Timber.d("verifiedId: ${signupInfoViewModel.verifiedId}, verifiedEmail: ${signupInfoViewModel.verifiedEmail}, verifiedCode: ${signupInfoViewModel.verifiedCode}")
        Timber.d("idOk: ${idOk}, emailOk: ${emailOk}, codeOk: ${codeOk}, pwOk: ${pwOk}")

        binding.btnSignup.isEnabled = allOk
        binding.btnSignup.isSelected = allOk
    }


    private fun clickSignupButton() {
        lifecycleScope.launch {
            signupInfoViewModel.signUpState.collect { state ->
                when (state) {
                    is SignUpState.Success -> {
                        findNavController().navigate(
                            R.id.action_signupInfoFragment_to_loginFragment,
                            null,
                            NavOptions.Builder()
                                .setPopUpTo(R.id.signupInfoFragment, true)
                                .setPopUpTo(R.id.signupAgreeFragment, true)
                                .build()
                        )
                    }

                    is SignUpState.Error -> {

                    }

                    is SignUpState.Loading -> {
                    }
                }
            }
        }

        binding.btnSignup.setOnClickListener {
            signupInfoViewModel.signUp(binding.etSignupPw.text.toString())
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