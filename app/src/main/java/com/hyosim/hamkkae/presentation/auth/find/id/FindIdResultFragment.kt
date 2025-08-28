package com.hyosim.hamkkae.presentation.auth.find.id

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hyosim.hamkkae.databinding.FragmentFindIdResultBinding
import com.hyosim.hamkkae.presentation.auth.find.FindActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

    @AndroidEntryPoint
class FindIdResultFragment : Fragment() {
    private var _binding: FragmentFindIdResultBinding? = null
    private val binding: FragmentFindIdResultBinding
        get() = requireNotNull(_binding) { "Find Result fragment is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindIdResultBinding.inflate(inflater, container, false)
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
            binding.tvUserId.text = it
        }

        binding.btnLogin.setOnClickListener {
            requireActivity().finish()
        }

        binding.btnFindPw.setOnClickListener {
            (activity as? FindActivity)?.selectTab(1)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}