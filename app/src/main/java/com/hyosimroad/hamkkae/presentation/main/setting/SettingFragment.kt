package com.hyosimroad.hamkkae.presentation.main.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.FragmentSettingBinding
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

        ClickChangePwButton()
        ClickCustomerCenterButton()
    }

    private fun ClickChangePwButton() {
        binding.tvChangePw.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_checkPwFragment)
        }
    }

    private fun ClickCustomerCenterButton() {
        binding.tvCustomerCenter.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_customerCenterFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}