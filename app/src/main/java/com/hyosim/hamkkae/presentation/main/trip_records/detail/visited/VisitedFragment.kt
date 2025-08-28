package com.hyosim.hamkkae.presentation.main.trip_records.detail.visited

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hyosim.hamkkae.databinding.FragmentTripRecordsDetailVisitedBinding
import timber.log.Timber

class VisitedFragment: Fragment() {
    private var _binding: FragmentTripRecordsDetailVisitedBinding? = null
    private val binding: FragmentTripRecordsDetailVisitedBinding
        get() = requireNotNull(_binding) { "detail overview fragment is null" }

    private val visitedViewModel: VisitedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripRecordsDetailVisitedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("Overview onViewCreated. rootSize=${view.width}x${view.height}")
        setting()
    }

    private fun setting() {
        setPlace()
    }

    private fun setPlace(){
        val visitedAdaptr = VisitedAdapter()
        binding.rvVisited.adapter = visitedAdaptr
        visitedAdaptr.submitList(visitedViewModel.visitedList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}