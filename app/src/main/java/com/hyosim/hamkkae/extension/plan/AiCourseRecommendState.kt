package com.hyosim.hamkkae.extension.plan

import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto


sealed class AiCourseRecommendState {
    object Loading : AiCourseRecommendState()
    data class Success(val courseList: List<AiCourseRecommendResponseDto>) : AiCourseRecommendState()
    data class Error(val message: String) : AiCourseRecommendState()
}