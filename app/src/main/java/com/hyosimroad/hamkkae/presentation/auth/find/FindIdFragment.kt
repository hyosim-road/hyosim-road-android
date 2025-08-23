package com.hyosimroad.hamkkae.presentation.auth.find

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.FragmentFindIdBinding
import timber.log.Timber

class FindIdFragment : Fragment() {
    private var _binding: FragmentFindIdBinding? = null
    private val binding: FragmentFindIdBinding
        get() = requireNotNull(_binding) { "Find fragment is null" }

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

    private fun setting(){
        checkEmail()
        checkCode()

        binding.btnSend.setOnClickListener{
            binding.clCode.visibility = View.VISIBLE
            binding.btnSend.visibility = View.GONE
        }

        binding.btnVerifyCode.setOnClickListener {
            (activity as? FindActivity)?.replaceFragment(FindIdResultFragment())
        }
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

    private fun checkCode(){
        binding.etCode.addTextChangedListener{ text->
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}