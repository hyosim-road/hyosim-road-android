package com.hyosimroad.hamkkae.data.datasource_impl

import com.hyosimroad.hamkkae.data.datasource.AuthDataSource
import com.hyosimroad.hamkkae.data.request_dto.auth.LoginRequestDto
import com.hyosimroad.hamkkae.data.request_dto.auth.SignUpRequestDto
import com.hyosimroad.hamkkae.data.request_dto.auth.VerifyIdEmailRequestDto
import com.hyosimroad.hamkkae.data.request_dto.auth.VerifyRequestDto
import com.hyosimroad.hamkkae.data.response_dto.auth.CheckIdResponseDto
import com.hyosimroad.hamkkae.data.response_dto.auth.EmailResponseDto
import com.hyosimroad.hamkkae.data.response_dto.auth.GetMyIdResponseDto
import com.hyosimroad.hamkkae.data.response_dto.auth.LoginResponseDto
import com.hyosimroad.hamkkae.data.response_dto.auth.SendResponseDto
import com.hyosimroad.hamkkae.data.response_dto.auth.VerifyIdEmailResponseDto
import com.hyosimroad.hamkkae.data.service.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService
): AuthDataSource{
    // login
    override suspend fun login(loginRequestDto: LoginRequestDto): LoginResponseDto = authService.login(loginRequestDto)

    // sign up
    override suspend fun checkId(id: String): CheckIdResponseDto = authService.checkId(id)

    override suspend fun send(email: String): SendResponseDto = authService.send(email)
    override suspend fun verify(verifyRequestDto: VerifyRequestDto): EmailResponseDto = authService.verify(verifyRequestDto)
    override suspend fun signUp(signUpRequestDto: SignUpRequestDto): SendResponseDto = authService.signUp(signUpRequestDto)

    // find
    override suspend fun getMyId(email: String): GetMyIdResponseDto = authService.getMyId(email)
    override suspend fun verifyIdEmail(verifyIdEmailRequestDto: VerifyIdEmailRequestDto): VerifyIdEmailResponseDto = authService.verifyIdEmail(verifyIdEmailRequestDto)
}