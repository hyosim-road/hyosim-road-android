package com.hyosimroad.hamkkae.presentation.main.setting.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.FragmentCheckPwBinding
import com.hyosimroad.hamkkae.extension.setting.CheckPwState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CheckPwFragment : Fragment() {
    private var _binding: FragmentCheckPwBinding? = null
    private val binding: FragmentCheckPwBinding
        get() = requireNotNull(_binding) { "setting fragment is null" }

    private val pwViewModel: ChangePwViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckPwBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("LoginFragment started!")

        checkPw()
    }

    private fun checkPw() {
        lifecycleScope.launch {
            pwViewModel.checkPwState.collect { state ->
                when (state) {
                    is CheckPwState.Success -> {
                        if (state.isCorrect) {
                            findNavController().navigate(R.id.action_checkPwFragment_to_changePwFragment)
                        } else {
                            binding.tvError.text = getString(R.string.signup_info_pw_not_match)
                            binding.mcvCheckPw.strokeColor =
                                requireContext().getColor(R.color.auth_no_red)
                        }
                    }

                    is CheckPwState.Error -> {
                        // 에러 처리
                    }

                    is CheckPwState.Loading -> {
                        // 로딩 중 처리
                    }
                }
            }
        }

        binding.etSignupPw.addTextChangedListener { text ->
            binding.btnCheckPw.apply {
                isEnabled = !text.isNullOrBlank()
                if (isEnabled) {
                    isSelected = true
                } else {
                    isSelected = false
                }
            }
        }

        binding.btnCheckPw.setOnClickListener {
            val password = binding.etSignupPw.text.toString()
            pwViewModel.checkPw(password)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}