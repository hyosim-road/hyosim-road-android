package com.hyosim.hamkkae.domain.repository

import com.hyosim.hamkkae.data.response_dto.auth.SendResponseDto
import com.hyosim.hamkkae.data.response_dto.setting.CheckPasswordResponseDto

interface SettingRepository {
    // change password
    suspend fun checkPw(password:String): Result<CheckPasswordResponseDto>
    suspend fun resetPw(password:String): Result<SendResponseDto>

}