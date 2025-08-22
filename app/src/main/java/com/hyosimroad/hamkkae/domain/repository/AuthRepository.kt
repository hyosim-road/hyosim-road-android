package com.hyosimroad.hamkkae.domain.repository

import com.hyosimroad.hamkkae.data.request_dto.login.LoginRequestDto
import com.hyosimroad.hamkkae.data.response_dto.login.CheckIdResponseDto
import com.hyosimroad.hamkkae.data.response_dto.login.EmailResponseDto
import com.hyosimroad.hamkkae.data.response_dto.login.LoginResponseDto
import com.hyosimroad.hamkkae.data.response_dto.login.SendResponseDto

interface AuthRepository {
    // login
    suspend fun login(
        email: String,
        pw: String
    ): Result<LoginResponseDto>

    // sign up
    suspend fun checkId(id:String):Result<CheckIdResponseDto>
    suspend fun send(
        email: String
    ): Result<SendResponseDto>
    suspend fun verify(email:String, code:String): Result<EmailResponseDto>
    suspend fun signUp(
        id: String,
        pw: String,
        email: String
    ): Result<EmailResponseDto>
}