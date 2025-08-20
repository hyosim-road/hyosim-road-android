package com.hyosimroad.hamkkae.presentation.main.setting.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.FragmentCheckPwBinding
import timber.log.Timber

class CheckPwFragment: Fragment() {
    private var _binding: FragmentCheckPwBinding? = null
    private val binding: FragmentCheckPwBinding
        get() = requireNotNull(_binding) { "setting fragment is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckPwBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("LoginFragment started!")

        ClickCheckPwButton()
    }

    private fun ClickCheckPwButton() {
        binding.btnCheckPw.setOnClickListener {
            findNavController().navigate(R.id.action_checkPwFragment_to_changePwFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}