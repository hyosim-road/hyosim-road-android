package com.hyosimroad.hamkkae.data.repository_impl

import com.hyosimroad.hamkkae.data.datasource.AuthDataSource
import com.hyosimroad.hamkkae.data.request_dto.login.LoginRequestDto
import com.hyosimroad.hamkkae.data.request_dto.login.VerifyRequestDto
import com.hyosimroad.hamkkae.data.response_dto.login.CheckIdResponseDto
import com.hyosimroad.hamkkae.data.response_dto.login.EmailResponseDto
import com.hyosimroad.hamkkae.data.response_dto.login.LoginResponseDto
import com.hyosimroad.hamkkae.data.response_dto.login.SendResponseDto
import com.hyosimroad.hamkkae.domain.repository.AuthRepository
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthDataSource
): AuthRepository {
    // login
    override suspend fun login(email: String, pw: String): Result<LoginResponseDto> {
        return runCatching {
            dataSource.login(LoginRequestDto(email, pw))
        }.onFailure {
            Timber.e("auth repository login fail: $it")
        }
    }

    // sign up
    override suspend fun checkId(id: String): Result<CheckIdResponseDto> {
        return runCatching {
            dataSource.checkId(id)
        }.onFailure {
            Timber.e("auth repository checkId fail: $it")
        }
    }

    override suspend fun send(email: String): Result<SendResponseDto> {
        return runCatching {
            dataSource.send(email)
        }.onFailure {
            Timber.e("auth repository send fail: $it")
        }
    }

    override suspend fun verify(
        email: String,
        code: String
    ): Result<EmailResponseDto> {
        return runCatching {
            dataSource.verify(VerifyRequestDto(email, code))
        }.onFailure {
            Timber.e("auth repository verify fail: $it")
        }
    }

    override suspend fun signUp(id: String, pw: String, email: String): Result<EmailResponseDto> {
        return runCatching {
            dataSource.signUp(com.hyosimroad.hamkkae.data.request_dto.login.SignUpRequestDto(id, pw, email))
        }.onFailure {
            Timber.e("auth repository signUp fail: $it")
        }
    }
}