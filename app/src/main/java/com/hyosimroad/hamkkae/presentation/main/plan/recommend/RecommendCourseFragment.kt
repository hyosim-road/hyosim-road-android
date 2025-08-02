package com.hyosimroad.hamkkae.presentation.main.plan.recommend

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hyosimroad.hamkkae.R
import com.hyosimroad.hamkkae.databinding.FragmentRecommendCourseBinding
import com.hyosimroad.hamkkae.presentation.main.plan.recommend.adapter.RecommendCourseAdapter
import timber.log.Timber

class RecommendCourseFragment : Fragment() {
    private var _binding: FragmentRecommendCourseBinding? = null
    private val binding: FragmentRecommendCourseBinding
        get() = requireNotNull(_binding) { "home fragment is null" }

    private val recommendCourseViewModel: RecommendCourseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("recommendCourseFragment started!")
        setting()
    }

    private fun setting() {
        // 초기값 설정
        binding.pbTrip.progress = 50

        // 애니메이션 적용
        val animator = ObjectAnimator.ofInt(binding.pbTrip, "progress", 50, 75)
        animator.duration = 1000 // 1초
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()

        val recommendCourseAdapter = RecommendCourseAdapter(
            clickItem = { course ->
                binding.btnNext.isSelected = true
            },
            clickDetail = { course ->
                findNavController().navigate(R.id.action_recommendCourseFragment_to_recommendDetailFragment)
            }
        )
        binding.rvCourse.adapter = recommendCourseAdapter
        recommendCourseAdapter.submitList(recommendCourseViewModel.courseList)

        clickNext()
    }

    private fun clickNext(){
        binding.btnNext.setOnClickListener {
            if(binding.btnNext.isSelected) findNavController().navigate(R.id.action_recommendCourseFragment_to_tripStartFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}