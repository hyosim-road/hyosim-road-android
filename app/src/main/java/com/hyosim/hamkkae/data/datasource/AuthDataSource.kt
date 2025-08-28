package com.hyosim.hamkkae.data.datasource

import com.hyosim.hamkkae.data.request_dto.auth.LoginRequestDto
import com.hyosim.hamkkae.data.request_dto.auth.SignUpRequestDto
import com.hyosim.hamkkae.data.request_dto.auth.VerifyIdEmailRequestDto
import com.hyosim.hamkkae.data.request_dto.auth.VerifyRequestDto
import com.hyosim.hamkkae.data.response_dto.auth.CheckIdResponseDto
import com.hyosim.hamkkae.data.response_dto.auth.EmailResponseDto
import com.hyosim.hamkkae.data.response_dto.auth.GetMyIdResponseDto
import com.hyosim.hamkkae.data.response_dto.auth.LoginResponseDto
import com.hyosim.hamkkae.data.response_dto.auth.SendResponseDto
import com.hyosim.hamkkae.data.response_dto.auth.VerifyIdEmailResponseDto

interface AuthDataSource {
    // login
    suspend fun login(loginRequestDto: LoginRequestDto): LoginResponseDto

    // sign up
    suspend fun checkId(id:String): CheckIdResponseDto
    suspend fun send(email:String): SendResponseDto
    suspend fun verify(verifyRequestDto: VerifyRequestDto): EmailResponseDto
    suspend fun signUp(signUpRequestDto: SignUpRequestDto): SendResponseDto

    // find
    suspend fun getMyId(email:String): GetMyIdResponseDto
    suspend fun verifyIdEmail(verifyIdEmailRequestDto: VerifyIdEmailRequestDto): VerifyIdEmailResponseDto
}