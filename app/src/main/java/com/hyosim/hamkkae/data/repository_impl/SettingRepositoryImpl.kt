package com.hyosim.hamkkae.data.repository_impl

import com.hyosim.hamkkae.data.datasource.SettingDataSource
import com.hyosim.hamkkae.data.request_dto.setting.InquiryRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.setting.CheckPasswordResponseData
import com.hyosim.hamkkae.domain.repository.SettingRepository
import timber.log.Timber
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val settingDataSource: SettingDataSource,
) : SettingRepository {
    override suspend fun checkPw(
        password: String
    ): Result<ApiResponse<CheckPasswordResponseData>> {
        return runCatching {
            settingDataSource.checkPw( password)
        }.onFailure {
            Timber.e("setting repository check pw fail: $it")
        }
    }

    override suspend fun resetPw(
        password: String
    ): Result<ApiResponse<Unit>> {
        return runCatching {
            settingDataSource.resetPw(password)
        }.onFailure {
            Timber.e("setting repository reset pw fail: $it")
        }
    }

    override suspend fun inquiry(inquiryRequestDto: InquiryRequestDto): Result<ApiResponse<Int>> {
        return runCatching {
            settingDataSource.inquiry(inquiryRequestDto)
        }.onFailure {
            Timber.e("setting repository inquiry fail: $it")
        }
    }

    override suspend fun resignation(): Result<ApiResponse<Unit>> {
        return runCatching {
            settingDataSource.resignation()
        }.onFailure {
            Timber.e("setting repository resignation fail: $it")
        }
    }
}