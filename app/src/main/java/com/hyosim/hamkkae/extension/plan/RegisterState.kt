package com.hyosim.hamkkae.extension.plan

import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import com.hyosim.hamkkae.data.response_dto.plan.CourseRecommendResponseData

sealed class RegisterState {
    data class Success(val course: AiCourseRecommendResponseDto) : RegisterState()
    data class Error(val message: String) : RegisterState()
    object Loading : RegisterState()
}