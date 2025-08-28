package com.hyosim.hamkkae.data.repository_impl

import com.hyosim.hamkkae.data.datasource.AuthDataSource
import com.hyosim.hamkkae.data.request_dto.auth.LoginRequestDto
import com.hyosim.hamkkae.data.request_dto.auth.VerifyRequestDto
import com.hyosim.hamkkae.data.response_dto.auth.CheckIdResponseDto
import com.hyosim.hamkkae.data.response_dto.auth.EmailResponseDto
import com.hyosim.hamkkae.data.response_dto.auth.GetMyIdResponseDto
import com.hyosim.hamkkae.data.response_dto.auth.LoginResponseDto
import com.hyosim.hamkkae.data.response_dto.auth.SendResponseDto
import com.hyosim.hamkkae.data.response_dto.auth.VerifyIdEmailResponseDto
import com.hyosim.hamkkae.domain.repository.AuthRepository
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

    override suspend fun signUp(id: String, pw: String, email: String): Result<SendResponseDto> {
        return runCatching {
            dataSource.signUp(com.hyosim.hamkkae.data.request_dto.auth.SignUpRequestDto(id, pw, email))
        }.onFailure {
            Timber.e("auth repository signUp fail: $it")
        }
    }

    // find
    override suspend fun getMyId(email: String): Result<GetMyIdResponseDto> {
        return runCatching {
            dataSource.getMyId(email)
        }.onFailure {
            Timber.e("auth repository getMyId fail: $it")
        }
    }

    override suspend fun verifyIdEmail(
        id: String,
        email: String
    ): Result<VerifyIdEmailResponseDto> {
        return runCatching {
            dataSource.verifyIdEmail(com.hyosim.hamkkae.data.request_dto.auth.VerifyIdEmailRequestDto(id, email))
        }.onFailure {
            Timber.e("auth repository verifyIdEmail fail: $it")
        }
    }
}