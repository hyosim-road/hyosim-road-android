package com.hyosim.hamkkae.data.repository_impl

import com.hyosim.hamkkae.data.datasource.SettingDataSource
import com.hyosim.hamkkae.data.response_dto.auth.SendResponseDto
import com.hyosim.hamkkae.data.response_dto.setting.CheckPasswordResponseDto
import com.hyosim.hamkkae.domain.repository.SettingRepository
import com.hyosim.hamkkae.domain.repository.TokenRepository
import timber.log.Timber
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val settingDataSource: SettingDataSource,
    private val tokenRepository: TokenRepository
) : SettingRepository {
    override suspend fun checkPw(
        password: String
    ): Result<CheckPasswordResponseDto> {
        return runCatching {
            settingDataSource.checkPw( password)
        }.onFailure {
            Timber.e("setting repository check pw fail: $it")
        }
    }

    override suspend fun resetPw(
        password: String
    ): Result<SendResponseDto> {
        return runCatching {
            settingDataSource.resetPw(password)
        }.onFailure {
            Timber.e("setting repository reset pw fail: $it")
        }
    }
}