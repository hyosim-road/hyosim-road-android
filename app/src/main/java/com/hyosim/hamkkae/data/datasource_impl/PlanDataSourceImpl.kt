package com.hyosim.hamkkae.data.datasource_impl

import com.hyosim.hamkkae.data.datasource.PlanDataSource
import com.hyosim.hamkkae.data.request_dto.plan.CourseRecommendRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.plan.CourseRecommendResponseData
import com.hyosim.hamkkae.data.service.PlanService
import javax.inject.Inject

class PlanDataSourceImpl  @Inject constructor(
    private val planService: PlanService
): PlanDataSource{
    override suspend fun recommendCourse(courseRecommendRequestDto: CourseRecommendRequestDto): ApiResponse<CourseRecommendResponseData> = planService.recommendCourse(courseRecommendRequestDto)
}