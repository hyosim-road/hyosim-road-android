package com.hyosim.hamkkae.presentation.main.plan.recommend

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.compose.ui.unit.Velocity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
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
            //delay(3000)

            /*findNavController().navigate(
                R.id.action_recommendLoadingFragment_to_recommendCourseFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.loadingFragment, true) // ✅ LoadingFragment 스택에서 제거
                    .build()
            )*/
            // API 호출
            recommendCourse()
        }
    }

    private fun startDotAnimation() {
        lifecycleScope.launch {
            var dotCount = 0
            var messageToggle = false // 번갈아 메시지 선택

            while (isActive) {
                dotCount = (dotCount % 3) + 1
                val dots = ".".repeat(dotCount)

                val message = if (messageToggle) {
                    getString(R.string.loading, dots)
                } else {
                    getString(R.string.loading_max_time)
                }

                binding.tvLoading.text = message

                messageToggle = !messageToggle // true <-> false 토글
                delay(2000) // 1초마다 바꾸기 (원하는 속도에 맞게 조절)
            }
        }
    }

    private fun recommendCourse(){
        lifecycleScope.launch {
            planViewModel.aiCourseRecommendState.collect { state->
                when(state){
                    is AiCourseRecommendState.Success -> {
                        val course = state.courseList
                        val hasAttractions = course.get(0).itinerary.any { it.attractions.isNotEmpty() }

                        if (hasAttractions) {
                            findNavController().navigate(
                                R.id.action_recommendLoadingFragment_to_recommendCourseFragment,
                                null,
                                NavOptions.Builder()
                                    .setPopUpTo(R.id.loadingFragment, true)
                                    .build()
                            )
                        } else {
                            // 오류 화면 표시
                            with(binding) {
                                lottieLoading.visibility = View.GONE
                                tvLoading.visibility = View.GONE

                                ivError.visibility = View.VISIBLE
                                tvError.visibility = View.VISIBLE
                            }
                        }

                        planViewModel.resetState()
                    }
                    is AiCourseRecommendState.Error -> {
                        with(binding){
                            lottieLoading.visibility = View.GONE
                            tvLoading.visibility= View.GONE

                            ivError.visibility= View.VISIBLE
                            tvError.visibility= View.VISIBLE
                        }
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