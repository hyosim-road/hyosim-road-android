package com.hyosimroad.hamkkae.data.datasource_impl

import com.hyosimroad.hamkkae.data.datasource.SettingDataSource
import com.hyosimroad.hamkkae.data.response_dto.auth.SendResponseDto
import com.hyosimroad.hamkkae.data.response_dto.setting.CheckPasswordResponseDto
import com.hyosimroad.hamkkae.data.service.SettingService
import javax.inject.Inject

class SettingDataSourceImpl @Inject constructor(
    private val settingService: SettingService
): SettingDataSource {
    override suspend fun checkPw(accessToken: String, password: String): CheckPasswordResponseDto = settingService.checkPw(accessToken, password)
    override suspend fun resetPw(accessToken: String, password: String): SendResponseDto = settingService.resetPw(accessToken, password)

}