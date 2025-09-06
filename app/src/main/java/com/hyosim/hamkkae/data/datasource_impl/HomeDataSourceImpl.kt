package com.hyosim.hamkkae.data.datasource_impl

import com.hyosim.hamkkae.data.datasource.HomeDataSource
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.home.ProgressTripResponseDto
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import com.hyosim.hamkkae.data.service.HomeService
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(
    private val homeService: HomeService
): HomeDataSource {
    override suspend fun progressTrip(): ApiResponse<ProgressTripResponseDto> = homeService.progressTrip()
}