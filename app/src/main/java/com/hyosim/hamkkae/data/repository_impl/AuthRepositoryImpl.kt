package com.hyosim.hamkkae.data.repository_impl

import com.hyosim.hamkkae.data.datasource.AuthDataSource
import com.hyosim.hamkkae.data.request_dto.auth.LoginRequestDto
import com.hyosim.hamkkae.data.request_dto.auth.VerifyRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.auth.CheckIdResponseData
import com.hyosim.hamkkae.data.response_dto.auth.EmailResponseData
import com.hyosim.hamkkae.data.response_dto.auth.GetMyIdResponseData
import com.hyosim.hamkkae.data.response_dto.auth.LoginResponseData
import com.hyosim.hamkkae.data.response_dto.auth.VerifyIdEmailResponseData
import com.hyosim.hamkkae.domain.repository.AuthRepository
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthDataSource
): AuthRepository {
    // login
    override suspend fun login(email: String, pw: String): Result<ApiResponse<LoginResponseData>> {
        return runCatching {
            dataSource.login(LoginRequestDto(email, pw))
        }.onFailure {
            Timber.e("auth repository login fail: $it")
        }
    }

    // sign up
    override suspend fun checkId(id: String): Result<ApiResponse<CheckIdResponseData>> {
        return runCatching {
            dataSource.checkId(id)
        }.onFailure {
            Timber.e("auth repository checkId fail: $it")
        }
    }

    override suspend fun checkEmail(email: String): Result<ApiResponse<CheckIdResponseData>> {
        return runCatching {
            dataSource.checkEmail(email)
        }.onFailure {
            Timber.e("auth repository checkEmail fail: $it")
        }
    }

    override suspend fun send(email: String): Result<ApiResponse<Unit>> {
        return runCatching {
            dataSource.send(email)
        }.onFailure {
            Timber.e("auth repository send fail: $it")
        }
    }

    override suspend fun verify(
        email: String,
        code: String
    ): Result<ApiResponse<EmailResponseData>> {
        return runCatching {
            dataSource.verify(VerifyRequestDto(email, code))
        }.onFailure {
            Timber.e("auth repository verify fail: $it")
        }
    }

    override suspend fun signUp(id: String, pw: String, email: String): Result<ApiResponse<Unit>> {
        return runCatching {
            dataSource.signUp(com.hyosim.hamkkae.data.request_dto.auth.SignUpRequestDto(id, pw, email))
        }.onFailure {
            Timber.e("auth repository signUp fail: $it")
        }
    }

    // find
    override suspend fun getMyId(email: String): Result<ApiResponse<GetMyIdResponseData>> {
        return runCatching {
            dataSource.getMyId(email)
        }.onFailure {
            Timber.e("auth repository getMyId fail: $it")
        }
    }

    override suspend fun verifyIdEmail(
        id: String,
        email: String
    ): Result<ApiResponse<VerifyIdEmailResponseData>> {
        return runCatching {
            dataSource.verifyIdEmail(com.hyosim.hamkkae.data.request_dto.auth.VerifyIdEmailRequestDto(id, email))
        }.onFailure {
            Timber.e("auth repository verifyIdEmail fail: $it")
        }
    }

    override suspend fun sendTempPw(email: String): Result<ApiResponse<Unit>> {
        return runCatching {
            dataSource.sendTempPw(email)
        }.onFailure {
            Timber.e("auth repository sendTempPw fail: $it")
        }
    }
}