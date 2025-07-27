package com.hyosimroad.hamkkae.presentation.main.plan.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
        val recommendCourseAdapter = RecommendCourseAdapter(
            clickItem = { course ->
                binding.btnNext.isSelected = true
            }
        )
        binding.rvCourse.adapter = recommendCourseAdapter
        recommendCourseAdapter.submitList(recommendCourseViewModel.courseList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}