package com.hyosim.hamkkae.presentation.auth.find.pw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.FragmentFindPwResultBinding
import timber.log.Timber

class FindPwResultFragment: Fragment() {
    private var _binding: FragmentFindPwResultBinding? = null
    private val binding: FragmentFindPwResultBinding
        get() = requireNotNull(_binding) { "Find Result fragment is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindPwResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("FindResultFragment started!")
        setting()
    }

    private fun setting() {
        val email = arguments?.getString("email")
        email.let {
            binding.tvSendTo.text = getString(R.string.find_pw_send_to, it)
        }

        binding.btnLogin.setOnClickListener {
            requireActivity().finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}