package com.hyosim.hamkkae.domain.repository

import com.hyosim.hamkkae.data.request_dto.setting.InquiryRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.setting.CheckPasswordResponseData

interface SettingRepository {
    // change password
    suspend fun checkPw(password:String): Result<ApiResponse<CheckPasswordResponseData>>
    suspend fun resetPw(password:String): Result< ApiResponse<Unit>>
    suspend fun inquiry(inquiryRequestDto: InquiryRequestDto): Result<ApiResponse<Int>>
    suspend fun resignation():Result<ApiResponse<Unit>>

}