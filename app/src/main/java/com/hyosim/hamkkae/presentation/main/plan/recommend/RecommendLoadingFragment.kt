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
import com.hyosim.hamkkae.databinding.FragmentRecommendLoadingBinding
import com.hyosim.hamkkae.extension.plan.AiCourseRecommendState
import com.hyosim.hamkkae.extension.plan.CourseRecommendState
import com.hyosim.hamkkae.presentation.main.plan.PlanViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.getValue

@AndroidEntryPoint
class RecommendLoadingFragment : Fragment() {
    private var _binding: FragmentRecommendLoadingBinding? = null
    private val binding: FragmentRecommendLoadingBinding
        get() = requireNotNull(_binding) { "home fragment is null" }

    private val planViewModel: PlanViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("selectTripStyleFragment started!")
        setting()
    }

    private fun setting() {
        lifecycleScope.launch {
            // 점 애니메이션 시작
            startDotAnimation()

            // 3초 대기
            delay(3000)

            findNavController().navigate(R.id.action_recommendLoadingFragment_to_recommendCourseFragment)
            // API 호출
            //recommendCourse()
        }
    }

    private fun startDotAnimation() {
        lifecycleScope.launch {
            var dotCount = 0
            while (isActive) {
                dotCount = (dotCount % 3) + 1
                val dots = ".".repeat(dotCount)

                binding.tvLoading.text = getString(R.string.loading, dots)
                delay(500)
            }
        }
    }

    private fun recommendCourse(){
        lifecycleScope.launch {
            planViewModel.aiCourseRecommendState.collect { state->
                when(state){
                    is AiCourseRecommendState.Success -> {
                        findNavController().navigate(R.id.action_recommendLoadingFragment_to_recommendCourseFragment)
                    }
                    is AiCourseRecommendState.Error -> {

                    }
                    is AiCourseRecommendState.Loading -> {

                    }
                }
            }
        }

        planViewModel.aiRecommendCourse()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}