package com.hyosim.hamkkae.domain.repository

import com.hyosim.hamkkae.data.request_dto.plan.CourseRecommendRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.plan.CourseRecommendResponseData

interface PlanRepository {
    suspend fun recommendCourse(
        courseRecommendRequestDto: CourseRecommendRequestDto
    ): Result<ApiResponse<CourseRecommendResponseData>>

    suspend fun register(
        registerRequestDto: CourseRecommendResponseData
    ):Result<ApiResponse<Int>>
}