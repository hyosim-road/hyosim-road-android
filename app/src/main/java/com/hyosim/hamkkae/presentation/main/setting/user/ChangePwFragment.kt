package com.hyosim.hamkkae.presentation.main.setting.user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.FragmentChangePwBinding
import com.hyosim.hamkkae.domain.model.PasswordRule
import com.hyosim.hamkkae.extension.setting.ChangePwState
import com.hyosim.hamkkae.presentation.auth.signup.PasswordRuleAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ChangePwFragment : Fragment() {
    private var _binding: FragmentChangePwBinding? = null
    private val binding: FragmentChangePwBinding
        get() = requireNotNull(_binding) { "setting fragment is null" }
    private val pwViewModel: ChangePwViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePwBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("LoginFragment started!")

        binding.etNewPw.addTextChangedListener {
            checkPw(it.toString())
        }
        binding.etNewPwCheck.addTextChangedListener{
            checkPwAgain(it.toString())
        }

        changePw()
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
        val green = ContextCompat.getColor(context, R.color.auth_check_green)
        val red = ContextCompat.getColor(context, R.color.auth_no_red)

        binding.mcvNewPw.strokeColor = (
                when {
                    password.isEmpty() -> gray                // 입력 없음
                    rules.all { it.isSatisfied } -> green     // 모두 충족
                    else -> red                               // 일부만 충족
                })

        checkPwAvailable()
    }

    private fun checkPwAgain(password: String) {
        val pw = binding.etNewPw.text.toString()
        binding.tvSamePw.apply {
            if (pw == password && !password.isEmpty()) {
                visibility = View.VISIBLE
                text = getString(R.string.signup_info_pw_match)
                setTextColor(ContextCompat.getColor(context, R.color.auth_check_green))
                checkPwAvailable()
            }else{
                text = getString(R.string.signup_info_pw_not_match)
                setTextColor(ContextCompat.getColor(context, R.color.auth_no_red))
            }
        }
    }

    private fun checkPwAvailable() {
        val pwOk = !binding.etNewPw.text.isNullOrBlank()
        val pwCheckOk = !binding.etNewPwCheck.text.isNullOrBlank()
        val samePw = binding.etNewPw.text.toString() == binding.etNewPwCheck.text.toString()

        if (pwOk && pwCheckOk && samePw) {
            binding.btnChangePw.isEnabled = true
            binding.btnChangePw.isSelected = true
        }
    }

    private fun changePw() {
        lifecycleScope.launch {
            pwViewModel.changePwState.collect { state ->
                when (state) {
                    is ChangePwState.Success -> {
                        // 스택의 모든 fragment 삭제
                        findNavController().navigate(
                            R.id.settingFragment,
                            null,
                            navOptions {
                                popUpTo(findNavController().graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        )
                    }
                    is ChangePwState.Error->{
                        // 인터넷 오류..
                    }
                    is ChangePwState.Loading->{}
                }
            }
        }

        binding.btnChangePw.setOnClickListener {
            val newPw = binding.etNewPw.text.toString()
            pwViewModel.changePw(newPw)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}