package com.hyosimroad.hamkkae.domain.repository

import com.hyosimroad.hamkkae.data.response_dto.auth.SendResponseDto
import com.hyosimroad.hamkkae.data.response_dto.setting.CheckPasswordResponseDto

interface SettingRepository {
    // change password
    suspend fun checkPw(accessToken:String, password:String): Result<CheckPasswordResponseDto>
    suspend fun resetPw(accessToken:String, password:String): Result<SendResponseDto>

}