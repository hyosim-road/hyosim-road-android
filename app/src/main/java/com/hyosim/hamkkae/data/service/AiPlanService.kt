package com.hyosim.hamkkae.data.service

import com.hyosim.hamkkae.data.request_dto.plan.CourseRecommendRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import com.hyosim.hamkkae.data.response_dto.plan.CourseRecommendResponseData
import retrofit2.http.Body
import retrofit2.http.POST

interface AiPlanService {
    @POST("/itineraries")
    suspend fun recommendCourse(
        @Body courseRecommendRequestDto: CourseRecommendRequestDto
    ): List<AiCourseRecommendResponseDto>
}