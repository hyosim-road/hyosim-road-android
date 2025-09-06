package com.hyosim.hamkkae.data.repository_impl

import com.hyosim.hamkkae.data.datasource.PlanDataSource
import com.hyosim.hamkkae.data.request_dto.plan.CourseRecommendRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.plan.CourseRecommendResponseData
import com.hyosim.hamkkae.domain.repository.PlanRepository
import timber.log.Timber
import javax.inject.Inject

class PlanRepositoryImpl @Inject constructor(
    private val planDataSource: PlanDataSource
): PlanRepository {
    override suspend fun recommendCourse(courseRecommendRequestDto: CourseRecommendRequestDto): Result<ApiResponse<CourseRecommendResponseData>> {
        return runCatching {
            planDataSource.recommendCourse(courseRecommendRequestDto)
        }.onFailure {
            Timber.e("plan repository recommend course fail: $it")
        }
    }
}