package com.hyosim.hamkkae.data.repository_impl

import com.hyosim.hamkkae.data.datasource.HomeDataSource
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.home.ProgressTripResponseDto
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import com.hyosim.hamkkae.domain.repository.HomeRepository
import timber.log.Timber
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource
): HomeRepository {
    override suspend fun progressTrip(): Result<ApiResponse<ProgressTripResponseDto>> {
        return runCatching {
            homeDataSource.progressTrip()
        }.onFailure {
            Timber.e("home repository progressTrip fail: $it")
        }
    }
}