package com.hyosimroad.hamkkae.presentation.main.setting.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.FragmentChangePwBinding
import timber.log.Timber

class ChangePwFragment: Fragment() {
    private var _binding: FragmentChangePwBinding? = null
    private val binding: FragmentChangePwBinding
        get() = requireNotNull(_binding) { "setting fragment is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePwBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("LoginFragment started!")

        ClickChangePwButton()
    }

    private fun ClickChangePwButton() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}