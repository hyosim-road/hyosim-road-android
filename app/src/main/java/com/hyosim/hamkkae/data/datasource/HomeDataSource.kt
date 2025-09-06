package com.hyosim.hamkkae.data.datasource

import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.home.ProgressTripResponseDto
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto

interface HomeDataSource {
    suspend fun progressTrip(): ApiResponse<ProgressTripResponseDto>
}