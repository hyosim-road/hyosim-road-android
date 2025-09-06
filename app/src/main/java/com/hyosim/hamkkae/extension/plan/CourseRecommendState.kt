package com.hyosim.hamkkae.extension.plan

import com.hyosim.hamkkae.data.response_dto.plan.CourseRecommendResponseData

sealed class CourseRecommendState {
    object Loading : CourseRecommendState()
    data class Success(val courses: CourseRecommendResponseData) : CourseRecommendState()
    data class Error(val message: String) : CourseRecommendState()
}