package com.hyosim.hamkkae.data.datasource_impl

import com.hyosim.hamkkae.data.datasource.AiPlanDataSource
import com.hyosim.hamkkae.data.request_dto.plan.CourseRecommendRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.plan.AiCourseRecommendResponseDto
import com.hyosim.hamkkae.data.response_dto.plan.CourseRecommendResponseData
import com.hyosim.hamkkae.data.service.AiPlanService
import com.hyosim.hamkkae.data.service.PlanService
import javax.inject.Inject

class AiPlanDataSourceImpl  @Inject constructor(
    private val aiPlanService: AiPlanService
): AiPlanDataSource {
    override suspend fun recommendCourse(courseRecommendRequestDto: CourseRecommendRequestDto): List<AiCourseRecommendResponseDto> = aiPlanService.recommendCourse(courseRecommendRequestDto)
}