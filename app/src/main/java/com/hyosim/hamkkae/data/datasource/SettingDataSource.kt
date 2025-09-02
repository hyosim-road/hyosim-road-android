package com.hyosim.hamkkae.data.datasource

import com.hyosim.hamkkae.data.request_dto.setting.InquiryRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.setting.CheckPasswordResponseData

interface SettingDataSource {
    // change password
    suspend fun checkPw( password:String): ApiResponse<CheckPasswordResponseData>
    suspend fun resetPw(password:String): ApiResponse<Unit>
    suspend fun inquiry(inquiryRequestDto: InquiryRequestDto): ApiResponse<Int>
    suspend fun resignation(): ApiResponse<Unit>
}