package com.hyosim.hamkkae.presentation.main.plan.recommend

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.hyosim.hamkkae.R
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import com.hyosim.hamkkae.data.response_dto.plan.toProgressTripResponseDto
import com.hyosim.hamkkae.databinding.FragmentRecommendCourseBinding
import com.hyosim.hamkkae.extension.plan.AiCourseRecommendState
import com.hyosim.hamkkae.extension.plan.RegisterState
import com.hyosim.hamkkae.presentation.main.map.MapActivity
import com.hyosim.hamkkae.presentation.main.plan.PlanViewModel
import com.hyosim.hamkkae.presentation.main.plan.recommend.adapter.RecommendCourseAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
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
    }

    private fun showCourse(){
        val currentState = planViewModel.aiCourseRecommendState.value
        if(currentState is AiCourseRecommendState.Success) {
            val courseList = currentState.courseList
            val recommendCourseAdapter = RecommendCourseAdapter(
                clickItem = { course ->
                    binding.btnNext.isSelected = true
                    clickNext(course)
                },
                clickDetail = { course ->
                    val action =
                        RecommendCourseFragmentDirections.actionRecommendCourseFragmentToRecommendDetailFragment(
                            course, planViewModel.travelStyle.value!!
                        )
                    findNavController().navigate(action)
                },
                clickMap = {course->
                    val progressCourse = course.toProgressTripResponseDto()

                    val intent = Intent(requireActivity(), MapActivity::class.java)
                    intent.putExtra("course", progressCourse)
                    startActivity(intent)
                }
            )
            binding.rvCourse.adapter = recommendCourseAdapter
            recommendCourseAdapter.submitList(courseList)
            recommendCourseAdapter.saveStyle(planViewModel.travelStyle.value.orEmpty())

            clickAgain()
        }

       /* val courseList = recommendCourseViewModel.mockCourses
        val recommendCourseAdapter = RecommendCourseAdapter(
            clickItem = { course ->
                binding.btnNext.isSelected = true
                clickNext(course)
            },
            clickDetail = { course ->
                val action =
                    RecommendCourseFragmentDirections.actionRecommendCourseFragmentToRecommendDetailFragment(
                        course, planViewModel.travelStyle.value!!
                    )
                findNavController().navigate(action)
            },
            clickMap = {course->
                val progressCourse = course.toProgressTripResponseDto()

                val intent = Intent(requireActivity(), MapActivity::class.java)
                intent.putExtra("course", progressCourse)
                startActivity(intent)
            }
        )
        binding.rvCourse.adapter = recommendCourseAdapter
        recommendCourseAdapter.submitList(courseList)
        recommendCourseAdapter.saveStyle(planViewModel.travelStyle.value.orEmpty())

        clickAgain()*/
    }

    private fun clickNext(course: AiCourseRecommendResponseDto) {
        binding.btnNext.setOnClickListener {
            if(binding.btnNext.isSelected) {
                register()
                recommendCourseViewModel.register(planViewModel.toRequestDto(), course)
            }
        }
    }
    private fun register(){
        lifecycleScope.launch {
            recommendCourseViewModel.registerState.collect { state ->
                when (state) {
                    is RegisterState.Success -> {
                        val action = RecommendCourseFragmentDirections
                            .actionRecommendCourseFragmentToTripStartFragment(state.course)

                        findNavController().navigate(action)

                    }

                    is RegisterState.Error -> {
                    }

                    is RegisterState.Loading -> {
                    }
                }
            }
        }
    }

    private fun clickAgain(){
        binding.btnMore.setOnClickListener {
            findNavController().navigate(
                R.id.action_recommendCourseFramgent_to_loadingFramgent,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.recommendCourseFragment, true) // ✅ LoadingFragment 스택에서 제거
                    .build()
            )
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}