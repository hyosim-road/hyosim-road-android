package com.hyosim.hamkkae.data.repository_impl

import com.hyosim.hamkkae.data.datasource.AiPlanDataSource
import com.hyosim.hamkkae.data.datasource.PlanDataSource
import com.hyosim.hamkkae.data.request_dto.plan.CourseRecommendRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import com.hyosim.hamkkae.data.response_dto.plan.CourseRecommendResponseData
import com.hyosim.hamkkae.domain.repository.AiPlanRepository
import com.hyosim.hamkkae.domain.repository.PlanRepository
import timber.log.Timber
import javax.inject.Inject

class AiPlanRepositoryImpl @Inject constructor(
    private val aiPlanDataSource: AiPlanDataSource
): AiPlanRepository {
    override suspend fun recommendCourse(courseRecommendRequestDto: CourseRecommendRequestDto): Result<List<AiCourseRecommendResponseDto>> {
        return runCatching {
            aiPlanDataSource.recommendCourse(courseRecommendRequestDto)
        }.onFailure {
            Timber.e("plan repository recommend course fail: $it")
        }
    }
}