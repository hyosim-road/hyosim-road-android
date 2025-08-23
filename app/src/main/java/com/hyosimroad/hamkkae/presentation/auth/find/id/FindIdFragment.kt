package com.hyosimroad.hamkkae.presentation.auth.find.id

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.FragmentFindIdBinding
import com.hyosimroad.hamkkae.extension.auth.CodeState
import com.hyosimroad.hamkkae.extension.auth.GetIdState
import com.hyosimroad.hamkkae.extension.auth.SendEmailState
import com.hyosimroad.hamkkae.presentation.auth.find.FindActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class FindIdFragment : Fragment() {
    private var _binding: FragmentFindIdBinding? = null
    private val binding: FragmentFindIdBinding
        get() = requireNotNull(_binding) { "Find fragment is null" }

    private val findIdViewModel: FindIdViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindIdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("FingFragment started!")
        setting()
    }

    private fun setting() {
        checkEmail()
        checkCode()

        sendEmail()
        verifyCode()
    }

    private fun checkEmail() {
        with(binding) {
            etEmail.addTextChangedListener {
                if (isValidEmail(it.toString())) {
                    tvAvailableEmail.visibility = View.GONE
                    icEmail.visibility = View.INVISIBLE

                    btnSend.isEnabled = true
                    btnSend.backgroundTintList =
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
                        ColorStateList.valueOf(requireContext().getColor(R.color.auth_no_red))
                    )

                    btnSend.isEnabled = false
                    btnSend.backgroundTintList =
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

    private fun checkCode() {
        binding.etCode.addTextChangedListener { text ->
            val input = text.toString()

            // 6자리 숫자 패턴 검사
            val isValid = input.matches(Regex("^\\d{6}$"))

            if (isValid) {
                binding.etCode.error = null
                binding.btnVerifyCode.isEnabled = true
                binding.btnVerifyCode.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.auth_box_orange))
            } else {
                if (input.isNotEmpty()) {
                    binding.etCode.error = "6자리 숫자를 입력하세요"
                }
                binding.btnVerifyCode.isEnabled = false
                binding.btnVerifyCode.backgroundTintList =
                    ColorStateList.valueOf(requireContext().getColor(R.color.auth_gray))
            }
        }
    }

    private fun sendEmail() {
        lifecycleScope.launch() {
            findIdViewModel.sendEmailState.collect { state ->
                when (state) {
                    SendEmailState.Loading -> {}
                    is SendEmailState.Success -> {
                        with(binding) {
                            setEmailButtonLoading(false)
                            tvAvailableEmail.apply {
                                visibility = View.VISIBLE
                                setTextColor(requireContext().getColor(R.color.auth_check_green))
                            }

                            clCode.visibility = View.VISIBLE
                            btnSend.visibility = View.GONE

                            // 5분 타이머 시작 (300초 = 300000ms)
                            object : CountDownTimer(5 * 60 * 1000, 1000) {
                                override fun onTick(millisUntilFinished: Long) {
                                    val minutes = (millisUntilFinished / 1000) / 60
                                    val seconds = (millisUntilFinished / 1000) % 60
                                    tvAvailableEmail.text =
                                        getString(R.string.signup_info_email_send) +
                                                " (${String.format("%02d:%02d", minutes, seconds)})"
                                    btnResend.isEnabled=false
                                }

                                override fun onFinish() {
                                    tvAvailableEmail.text =
                                        getString(R.string.signup_info_email_finished)
                                    tvAvailableEmail.setTextColor(requireContext().getColor(R.color.auth_no_red))
                                    btnResend.isEnabled= true
                                }
                            }.start()
                        }
                    }

                    is SendEmailState.Error -> {
                        Timber.d("send email state error: ${state.message}")
                    }
                }
            }
        }

        binding.btnSend.setOnClickListener {
            val email = binding.etEmail.text.toString()
            findIdViewModel.sendEmail(email)
            setEmailButtonLoading(true)
        }

        binding.btnResend.setOnClickListener {
            val email = binding.etEmail.text.toString()
            findIdViewModel.sendEmail(email)
            setEmailButtonLoading(true)
        }
    }

    private fun setEmailButtonLoading(isLoading: Boolean) {
        if (isLoading) {
            val grayColor = ContextCompat.getColor(requireContext(), R.color.auth_gray)
            binding.btnSend.apply {
                text = getString(R.string.signup_info_email_sending)
                isEnabled = false
                backgroundTintList = ColorStateList.valueOf(grayColor)
            }
        } else {
            val orangeColor = ContextCompat.getColor(requireContext(), R.color.auth_box_orange)
            binding.btnSend.isEnabled = true
            binding.btnSend.backgroundTintList = ColorStateList.valueOf(orangeColor)
        }
    }

    private fun verifyCode() {
        lifecycleScope.launch {
            findIdViewModel.codeState.collect { state ->
                when (state) {
                    CodeState.Loading -> {}
                    is CodeState.Success -> {
                        if (state.success) {
                            getEmail(state.email!!)
                            findIdViewModel.getId(state.email!!)
                        }

                    }

                    is CodeState.Error -> {
                        Timber.d("code state error: ${state.message}")
                    }
                }
            }
        }

        binding.btnVerifyCode.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val code = binding.etCode.text.toString()
            findIdViewModel.verifyCode(email, code)
        }
    }

    private fun getEmail(email: String) {
        lifecycleScope.launch {
            findIdViewModel.getIdState.collect { state ->
                when (state) {
                    is GetIdState.Success -> {
                        if (state.success)
                            (activity as? FindActivity)?.replaceFragment(
                                FindIdResultFragment(),
                                email
                            )
                        else {
                            binding.etCode.error = getString(R.string.signup_info_code_no_match)
                        }
                    }

                    is GetIdState.Error -> {
                        // 인터넷 에러..
                    }

                    is GetIdState.Loading -> {

                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}