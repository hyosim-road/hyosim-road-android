package com.hyosim.hamkkae.presentation.main.plan.recommend

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.databinding.FragmentRecommendCourseBinding
import com.hyosim.hamkkae.extension.plan.AiCourseRecommendState
import com.hyosim.hamkkae.extension.plan.CourseRecommendState
import com.hyosim.hamkkae.presentation.main.plan.PlanViewModel
import com.hyosim.hamkkae.presentation.main.plan.recommend.adapter.RecommendCourseAdapter
import timber.log.Timber

class RecommendCourseFragment : Fragment() {
    private var _binding: FragmentRecommendCourseBinding? = null
    private val binding: FragmentRecommendCourseBinding
        get() = requireNotNull(_binding) { "home fragment is null" }

    private val recommendCourseViewModel: RecommendCourseViewModel by viewModels()
    private val planViewModel: PlanViewModel by activityViewModels()

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

        showCourse()

        clickNext()
    }

    private fun showCourse(){
        //val currentState = planViewModel.aiCourseRecommendState.value
        //if(currentState is AiCourseRecommendState.Success){
            //val courseList = currentState.courseList
            val recommendCourseAdapter = RecommendCourseAdapter(
                clickItem = { course ->
                    binding.btnNext.isSelected = true
                },
                clickDetail = { course ->
                    val action = RecommendCourseFragmentDirections.actionRecommendCourseFragmentToRecommendDetailFragment(course)
                    findNavController().navigate(action)
                }
            )
            binding.rvCourse.adapter = recommendCourseAdapter
            recommendCourseAdapter.submitList(recommendCourseViewModel.mockCourses)

       // }
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