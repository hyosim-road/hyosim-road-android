package com.hyosimroad.hamkkae.data.datasource_impl

import com.hyosimroad.hamkkae.data.datasource.AuthDataSource
import com.hyosimroad.hamkkae.data.request_dto.login.LoginRequestDto
import com.hyosimroad.hamkkae.data.request_dto.login.SignUpRequestDto
import com.hyosimroad.hamkkae.data.request_dto.login.VerifyRequestDto
import com.hyosimroad.hamkkae.data.response_dto.login.CheckIdResponseDto
import com.hyosimroad.hamkkae.data.response_dto.login.EmailResponseDto
import com.hyosimroad.hamkkae.data.response_dto.login.LoginResponseDto
import com.hyosimroad.hamkkae.data.response_dto.login.SendResponseDto
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
    override suspend fun signUp(signUpRequestDto: SignUpRequestDto): EmailResponseDto = authService.signUp(signUpRequestDto)
}