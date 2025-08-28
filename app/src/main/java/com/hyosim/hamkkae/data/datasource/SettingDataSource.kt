package com.hyosim.hamkkae.data.datasource

import com.hyosim.hamkkae.data.response_dto.auth.SendResponseDto
import com.hyosim.hamkkae.data.response_dto.setting.CheckPasswordResponseDto

interface SettingDataSource {
    // change password
    suspend fun checkPw( password:String): CheckPasswordResponseDto
    suspend fun resetPw(password:String): SendResponseDto
}