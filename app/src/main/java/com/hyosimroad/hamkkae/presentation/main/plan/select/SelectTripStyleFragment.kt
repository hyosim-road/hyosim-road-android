package com.hyosimroad.hamkkae.presentation.main.plan.select

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.FragmentSelectTripStyleBinding
import com.hyosimroad.hamkkae.presentation.main.plan.select.adapter.SelectTripStyleAdapter
import timber.log.Timber

class SelectTripStyleFragment: Fragment() {
    private var _binding: FragmentSelectTripStyleBinding? = null
    private val binding: FragmentSelectTripStyleBinding
        get() = requireNotNull(_binding) { "home fragment is null" }

    private val selectTripStyleViewModel: SelectTripStyleViewModel by viewModels()

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
        setting()
    }

    private fun setting() {
        binding.pbTrip.progress = 50

        val animator = ObjectAnimator.ofInt(binding.pbTrip, "progress", 25, 50)
        animator.duration = 1000 // 1초
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()

        val tripStyleAdapter = SelectTripStyleAdapter { style, selected ->
            Timber.d("Clicked style=${style.name}, selected=$selected")
        }

        binding.rvTripStyle.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = tripStyleAdapter
            setHasFixedSize(true)
        }

        tripStyleAdapter.submitList(selectTripStyleViewModel.tripStyleList)

        binding.btnNext.isEnabled = true
        binding.btnNext.isSelected = true
        binding.tvDone.visibility = View.GONE

        clickNextButton()
    }

    private fun clickNextButton() {
        binding.btnNext.setOnClickListener {
            if (binding.btnNext.isEnabled) {
                findNavController().navigate(R.id.action_selectTripStyleFragment_to_recommendCourseFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}