package com.hyosim.hamkkae.presentation.main.plan.recommend.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val course = savedStateHandle.get<AiCourseRecommendResponseDto>("course")

    val allAttractions: List<AiCourseRecommendResponseDto.Itinerary.Attraction> =
        course?.itinerary?.flatMap { it.attractions } ?: emptyList()
}