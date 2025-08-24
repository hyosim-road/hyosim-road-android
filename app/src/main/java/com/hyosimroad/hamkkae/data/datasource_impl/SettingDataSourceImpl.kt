package com.hyosimroad.hamkkae.data.datasource_impl

import com.hyosimroad.hamkkae.data.datasource.SettingDataSource
import com.hyosimroad.hamkkae.data.response_dto.auth.SendResponseDto
import com.hyosimroad.hamkkae.data.response_dto.setting.CheckPasswordResponseDto
import com.hyosimroad.hamkkae.data.service.SettingService
import javax.inject.Inject

class SettingDataSourceImpl @Inject constructor(
    private val settingService: SettingService
): SettingDataSource {
    override suspend fun checkPw( password: String): CheckPasswordResponseDto = settingService.checkPw( password)
    override suspend fun resetPw(password: String): SendResponseDto = settingService.resetPw(password)

}