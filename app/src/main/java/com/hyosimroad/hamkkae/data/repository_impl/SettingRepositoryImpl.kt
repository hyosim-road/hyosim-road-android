package com.hyosimroad.hamkkae.data.repository_impl

import com.hyosimroad.hamkkae.data.datasource.AuthDataSource
import com.hyosimroad.hamkkae.data.datasource.SettingDataSource
import com.hyosimroad.hamkkae.data.datasource_impl.SettingDataSourceImpl
import com.hyosimroad.hamkkae.data.response_dto.auth.SendResponseDto
import com.hyosimroad.hamkkae.data.response_dto.setting.CheckPasswordResponseDto
import com.hyosimroad.hamkkae.domain.repository.SettingRepository
import timber.log.Timber
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
   private val settingDataSource: SettingDataSource
): SettingRepository {
    override suspend fun checkPw(
        accessToken: String,
        password: String
    ): Result<CheckPasswordResponseDto> {
        return runCatching {
            settingDataSource.checkPw(accessToken, password)
        }.onFailure {
            Timber.e("setting repository check pw fail: $it")
        }
    }

    override suspend fun resetPw(
        accessToken: String,
        password: String
    ): Result<SendResponseDto> {
        return runCatching {
            settingDataSource.resetPw(accessToken, password)
        }.onFailure {
            Timber.e("setting repository reset pw fail: $it")
        }
    }
}