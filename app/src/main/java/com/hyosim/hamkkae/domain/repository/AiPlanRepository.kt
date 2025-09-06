package com.hyosim.hamkkae.domain.repository

import com.hyosim.hamkkae.data.request_dto.plan.CourseRecommendRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import com.hyosim.hamkkae.data.response_dto.plan.CourseRecommendResponseData

interface AiPlanRepository {
    suspend fun recommendCourse(
        courseRecommendRequestDto: CourseRecommendRequestDto
    ): Result<List<AiCourseRecommendResponseDto>>
}