package com.hyosimroad.hamkkae.data.datasource

import com.hyosimroad.hamkkae.data.request_dto.login.LoginRequestDto
import com.hyosimroad.hamkkae.data.request_dto.login.SignUpRequestDto
import com.hyosimroad.hamkkae.data.request_dto.login.VerifyRequestDto
import com.hyosimroad.hamkkae.data.response_dto.login.CheckIdResponseDto
import com.hyosimroad.hamkkae.data.response_dto.login.EmailResponseDto
import com.hyosimroad.hamkkae.data.response_dto.login.LoginResponseDto
import com.hyosimroad.hamkkae.data.response_dto.login.SendResponseDto

interface AuthDataSource {
    // login
    suspend fun login(loginRequestDto: LoginRequestDto): LoginResponseDto

    // sign up
    suspend fun checkId(id:String): CheckIdResponseDto
    suspend fun send(email:String): SendResponseDto
    suspend fun verify(verifyRequestDto: VerifyRequestDto): EmailResponseDto
    suspend fun signUp(signUpRequestDto: SignUpRequestDto): EmailResponseDto
}