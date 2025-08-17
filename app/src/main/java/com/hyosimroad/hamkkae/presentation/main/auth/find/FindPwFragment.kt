package com.hyosimroad.hamkkae.presentation.main.auth.find

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hyosimroad.hamkkae.databinding.FragmentFindPwBinding
import timber.log.Timber

class FindPwFragment : Fragment() {
    private var _binding: FragmentFindPwBinding? = null
    private val binding: FragmentFindPwBinding
        get() = requireNotNull(_binding) { "FindPwFragment binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindPwBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("FindPwFragment started!")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}