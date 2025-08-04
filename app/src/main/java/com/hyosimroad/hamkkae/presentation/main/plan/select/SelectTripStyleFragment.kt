package com.hyosimroad.hamkkae.presentation.main.plan.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hyosimroad.hamkkae.databinding.FragmentSelectTripStyleBinding
import timber.log.Timber

class SelectTripStyleFragment: Fragment() {
    private var _binding: FragmentSelectTripStyleBinding? = null
    private val binding: FragmentSelectTripStyleBinding
        get() = requireNotNull(_binding) { "home fragment is null" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectTripStyleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("selectTripStyleFragment started!")
//        setting()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}