package com.hyosim.hamkkae.data.service

import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.home.ProgressTripResponseDto
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import retrofit2.http.GET

interface HomeService {
    @GET("/trip/ongoing")
    suspend fun progressTrip(): ApiResponse<ProgressTripResponseDto>
}