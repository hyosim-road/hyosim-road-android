package com.hyosim.hamkkae.presentation.main.setting

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.FragmentSettingBinding
import com.hyosim.hamkkae.presentation.auth.AuthActivity
import com.hyosim.hamkkae.util.SessionManager
import timber.log.Timber

class SettingFragment: Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding
        get() = requireNotNull(_binding) { "setting fragment is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("SettingFragment started!")


        binding.tvChangePw.setOnClickListener {
            navigate(R.id.action_settingFragment_to_checkPwFragment)
        }
        binding.tvCustomerCenter.setOnClickListener {
            navigate(R.id.action_settingFragment_to_customerCenterFragment)
        }
        binding.clResignation.setOnClickListener {
            navigate(R.id.action_settingFragment_to_resignationFragment)
        }

        binding.tvService.setOnClickListener {
            navigate(R.id.action_settingFragment_to_signup_service)
        }

        binding.tvPersonal.setOnClickListener {
            navigate(R.id.action_settingFragment_to_signup_personal)
        }

        binding.cvLogout.setOnClickListener {
            val sessionManager = SessionManager
            sessionManager.clearToken()

            val intent = Intent(requireContext(), AuthActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)

            requireActivity().finishAffinity()
        }

        binding.switchQuestion.isChecked = true
        binding.switchQuestion.setOnCheckedChangeListener { _, isChecked ->
            setSwitchState(isChecked)
        }
    }

    private fun navigate(resId:Int){
        findNavController().navigate(resId)
    }

    private fun setSwitchState(isChecked: Boolean) {
        if (isChecked) {
            binding.switchQuestion.trackTintList = ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.hover_orange)
            )
        } else {
            binding.switchQuestion.trackTintList = ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), R.color.auth_gray)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}