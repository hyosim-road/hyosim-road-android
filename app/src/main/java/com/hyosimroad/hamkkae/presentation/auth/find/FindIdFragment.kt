package com.hyosimroad.hamkkae.presentation.auth.find

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.FragmentFindIdBinding
import timber.log.Timber

class FindIdFragment : Fragment() {
    private var _binding: FragmentFindIdBinding? = null
    private val binding: FragmentFindIdBinding
        get() = requireNotNull(_binding) { "Find fragment is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindIdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("FingFragment started!")

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}