package com.hyosim.hamkkae.data.datasource

import com.hyosim.hamkkae.data.request_dto.plan.CourseRecommendRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.plan.CourseRecommendResponseData

interface PlanDataSource {
    suspend fun recommendCourse(
        courseRecommendRequestDto: CourseRecommendRequestDto
    ): ApiResponse<CourseRecommendResponseData>

    suspend fun register(
        registerRequestDto: CourseRecommendResponseData
    ): ApiResponse<Int>
}