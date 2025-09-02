package com.hyosim.hamkkae.data.datasource_impl

import com.hyosim.hamkkae.data.datasource.AuthDataSource
import com.hyosim.hamkkae.data.request_dto.auth.LoginRequestDto
import com.hyosim.hamkkae.data.request_dto.auth.SignUpRequestDto
import com.hyosim.hamkkae.data.request_dto.auth.VerifyIdEmailRequestDto
import com.hyosim.hamkkae.data.request_dto.auth.VerifyRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.auth.CheckIdResponseData
import com.hyosim.hamkkae.data.response_dto.auth.EmailResponseData
import com.hyosim.hamkkae.data.response_dto.auth.GetMyIdResponseData
import com.hyosim.hamkkae.data.response_dto.auth.LoginResponseData
import com.hyosim.hamkkae.data.response_dto.auth.VerifyIdEmailResponseData
import com.hyosim.hamkkae.data.service.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService
): AuthDataSource{
    // login
    override suspend fun login(loginRequestDto: LoginRequestDto): ApiResponse<LoginResponseData> = authService.login(loginRequestDto)

    // sign up
    override suspend fun checkId(id: String): ApiResponse<CheckIdResponseData> = authService.checkId(id)
    override suspend fun checkEmail(email: String): ApiResponse<CheckIdResponseData> = authService.checkEmail(email)

    override suspend fun send(email: String):  ApiResponse<Unit> = authService.send(email)
    override suspend fun verify(verifyRequestDto: VerifyRequestDto): ApiResponse<EmailResponseData> = authService.verify(verifyRequestDto)
    override suspend fun signUp(signUpRequestDto: SignUpRequestDto):  ApiResponse<Unit> = authService.signUp(signUpRequestDto)

    // find
    override suspend fun getMyId(email: String): ApiResponse<GetMyIdResponseData> = authService.getMyId(email)
    override suspend fun verifyIdEmail(verifyIdEmailRequestDto: VerifyIdEmailRequestDto):  ApiResponse<VerifyIdEmailResponseData> = authService.verifyIdEmail(verifyIdEmailRequestDto)
    override suspend fun sendTempPw(email: String):  ApiResponse<Unit> = authService.sendTempPw(email)
}