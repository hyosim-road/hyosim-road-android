package com.hyosim.hamkkae.domain.repository

import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.home.ProgressTripResponseDto
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto

interface HomeRepository {
    suspend fun progressTrip() : Result<ApiResponse<ProgressTripResponseDto>>
}