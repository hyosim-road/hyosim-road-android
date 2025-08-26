package com.hyosimroad.hamkkae.presentation.auth.find.pw

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.FragmentFindPwBinding
import com.hyosimroad.hamkkae.extension.auth.CodeState
import com.hyosimroad.hamkkae.extension.auth.SendEmailState
import com.hyosimroad.hamkkae.extension.auth.VerifyIdEmailState
import com.hyosimroad.hamkkae.presentation.auth.find.FindActivity
import com.hyosimroad.hamkkae.presentation.auth.find.id.FindIdResultFragment
import com.hyosimroad.hamkkae.presentation.main.setting.user.ChangePwFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class FindPwFragment : Fragment() {
    private var _binding: FragmentFindPwBinding? = null
    private val binding: FragmentFindPwBinding
        get() = requireNotNull(_binding) { "FindPwFragment binding is null" }
    private val findPwViewModel: FindPwViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindPwBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("FindPwFragment started!")
        setting()
    }

    private fun setting() {
        checkIdEmail()
        verifyIdEmail()
    }

    private fun checkIdEmail() {
        binding.etId.addTextChangedListener {
            val id = it.toString().trim()
            val regex = "^[a-zA-Z0-9]{6,12}$".toRegex()

            if (!id.matches(regex)) {
                with(binding) {
                    mcvId.strokeColor = requireContext().getColor(R.color.auth_no_red)

                    tvAvailableId.apply {
                        visibility = View.VISIBLE
                        text = getString(R.string.signup_info_id_rule)
                        setTextColor(requireContext().getColor(R.color.auth_no_red))
                    }

                    icId.visibility = View.VISIBLE
                    icId.setImageResource(R.drawable.ic_no_white_24)  // 아이콘 설정
                    ImageViewCompat.setImageTintList(
                        icId,
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.auth_no_red
                            )
                        )
                    )
                }
            } else {
                with(binding) {
                    mcvId.strokeColor =
                        requireContext().getColor(R.color.auth_check_green)


                    tvAvailableId.visibility = View.GONE
                    icId.visibility = View.GONE
                }

                if (isEnableButton()) {
                    binding.btnSend.apply {
                        isEnabled = true
                        backgroundTintList =
                            ColorStateList.valueOf(requireContext().getColor(R.color.auth_box_orange))
                    }
                }
            }
        }

        binding.etEmail.addTextChangedListener {
            val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
            if (it.toString().matches(regex)) {
                with(binding){
                    tvAvailableEmail.visibility = View.GONE
                    icEmail.visibility = View.INVISIBLE

                    if (isEnableButton()) {
                        binding.btnSend.apply {
                            isEnabled = true
                            backgroundTintList =
                                ColorStateList.valueOf(requireContext().getColor(R.color.auth_box_orange))
                        }
                    }
                }
            } else {
                with(binding){
                    tvAvailableEmail.apply {
                        visibility = View.VISIBLE
                        text = getString(R.string.signup_info_email_rule)
                        setTextColor(requireContext().getColor(R.color.auth_no_red))
                    }

                    icEmail.visibility = View.VISIBLE
                    icEmail.setImageResource(R.drawable.ic_no_white_24)  // 아이콘 설정
                    ImageViewCompat.setImageTintList(
                        binding.icEmail,
                        ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.auth_no_red))
                    )
                }

            }
        }
    }

    private fun isEnableButton(): Boolean {
        val id = binding.etId.text.toString()
        val email = binding.etEmail.text.toString()
        return id.isNotEmpty() && email.isNotEmpty()
    }

    private fun verifyIdEmail() {
        lifecycleScope.launch {
            findPwViewModel.verifyIdEmailState.collect { state ->
                when (state) {
                    is VerifyIdEmailState.Success -> {
                        sendEmail()

                        findPwViewModel.sendEmail(state.email)
                    }

                    is VerifyIdEmailState.Error -> {
                        Timber.e("verifyIdEmail state error!")
                        setEmailButtonLoading(false)
                    }

                    is VerifyIdEmailState.Loading -> {
                        // 로딩 중 처리
                    }
                }
            }
        }

        binding.btnSend.setOnClickListener {
            findPwViewModel.verifyIdEmail(
                binding.etId.text.toString(),
                binding.etEmail.text.toString()
            )
            setEmailButtonLoading(true)
        }
    }

    private fun sendEmail() {
        lifecycleScope.launch() {
            findPwViewModel.sendEmailState.collect { state ->
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
                                    btnResend.isEnabled = false
                                }

                                override fun onFinish() {
                                    tvAvailableEmail.text =
                                        getString(R.string.signup_info_email_finished)
                                    tvAvailableEmail.setTextColor(requireContext().getColor(R.color.auth_no_red))
                                    btnResend.isEnabled = true
                                }
                            }.start()

                            verifyCode()
                        }
                    }

                    is SendEmailState.Error -> {
                        Timber.d("send email state error: ${state.message}")
                        setEmailButtonLoading(false)

                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
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
            binding.btnSend.apply {
                text = getString(R.string.find_id_send)
                isEnabled = true
                backgroundTintList = ColorStateList.valueOf(orangeColor)
            }
        }
    }

    private fun verifyCode() {
        lifecycleScope.launch {
            findPwViewModel.codeState.collect { state ->
                when (state) {
                    CodeState.Loading -> {}
                    is CodeState.Success -> {
                        if (state.success) {
                            (activity as? FindActivity)?.replaceFragment(
                                ChangePwFragment(),
                                ""
                            )
                        } else {
                            with(binding) {
                                tvAvailableCode.apply {
                                    visibility = View.VISIBLE
                                    setTextColor(
                                        ContextCompat.getColor(
                                            requireContext(),
                                            R.color.auth_no_red
                                        )
                                    )
                                    text = getString(R.string.signup_info_code_no_match)
                                }
                                icCode.setImageResource(R.drawable.ic_no_white_24)
                                ImageViewCompat.setImageTintList(
                                    icCode,
                                    ColorStateList.valueOf(
                                        ContextCompat.getColor(
                                            requireContext(),
                                            R.color.auth_no_red
                                        )
                                    )
                                )
                            }
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
            findPwViewModel.verifyCode(email, code)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}