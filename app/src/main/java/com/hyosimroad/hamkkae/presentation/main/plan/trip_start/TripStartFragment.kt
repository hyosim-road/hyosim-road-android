package com.hyosimroad.hamkkae.presentation.main.plan.trip_start

import android.animation.ObjectAnimator
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hyosimroad.hamkkae.databinding.FragmentTripStartBinding
import com.hyosimroad.hamkkae.presentation.main.home.adapter.today_schedule.TodayScheduleAdapter
import timber.log.Timber
import kotlin.getValue

class TripStartFragment: Fragment() {
    private var _binding: FragmentTripStartBinding? = null
    private val binding: FragmentTripStartBinding
        get() = requireNotNull(_binding) { "home fragment is null" }

    private val tripStartViewModel: TripStartViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("recommendCourseFragment started!")
        setting()
    }

    private fun setting() {
        binding.pbTrip.progress = 75

        // 애니메이션 적용
        val animator = ObjectAnimator.ofInt(binding.pbTrip, "progress", 75, 100)
        animator.duration = 1000 // 1초
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()

        binding.btnStart.isSelected=true

        showPreview()
        clickStart()
    }

    private fun showPreview(){
        val adapter = TodayScheduleAdapter(true)
        binding.rvPreview.adapter = adapter
        adapter.submitList(tripStartViewModel.previewList)
    }

    private fun clickStart(){
        binding.btnStart.setOnClickListener {
            goMain()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            goMain()
        }
    }

    private fun goMain(){
        requireActivity().setResult(Activity.RESULT_OK) // ✅ 결과값 설정
        requireActivity().finish()                     // ✅ PlanActivity 종료
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}