package com.hyosim.hamkkae.presentation.main.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.FragmentSettingBinding
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

    }

    private fun navigate(resId:Int){
        findNavController().navigate(resId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}