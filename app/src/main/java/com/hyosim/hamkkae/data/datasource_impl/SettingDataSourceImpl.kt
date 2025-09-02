package com.hyosim.hamkkae.data.datasource_impl

import com.hyosim.hamkkae.data.datasource.SettingDataSource
import com.hyosim.hamkkae.data.request_dto.setting.InquiryRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.setting.CheckPasswordResponseData
import com.hyosim.hamkkae.data.service.SettingService
import javax.inject.Inject

class SettingDataSourceImpl @Inject constructor(
    private val settingService: SettingService
): SettingDataSource {
    override suspend fun checkPw( password: String): ApiResponse<CheckPasswordResponseData> = settingService.checkPw( password)
    override suspend fun resetPw(password: String):  ApiResponse<Unit> = settingService.resetPw(password)
    override suspend fun inquiry(inquiryRequestDto: InquiryRequestDto): ApiResponse<Int>  = settingService.inquiry(inquiryRequestDto)
}