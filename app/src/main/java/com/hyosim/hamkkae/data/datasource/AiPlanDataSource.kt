package com.hyosim.hamkkae.data.datasource

import com.hyosim.hamkkae.data.request_dto.plan.CourseRecommendRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import com.hyosim.hamkkae.data.response_dto.plan.CourseRecommendResponseData

interface AiPlanDataSource {
    suspend fun recommendCourse(
        courseRecommendRequestDto: CourseRecommendRequestDto
    ): List<AiCourseRecommendResponseDto>
}