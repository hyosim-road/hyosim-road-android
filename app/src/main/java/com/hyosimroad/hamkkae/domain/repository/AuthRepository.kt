package com.hyosimroad.hamkkae.domain.repository

import com.hyosimroad.hamkkae.data.response_dto.auth.CheckIdResponseDto
import com.hyosimroad.hamkkae.data.response_dto.auth.EmailResponseDto
import com.hyosimroad.hamkkae.data.response_dto.auth.GetMyIdResponseDto
import com.hyosimroad.hamkkae.data.response_dto.auth.LoginResponseDto
import com.hyosimroad.hamkkae.data.response_dto.auth.SendResponseDto
import com.hyosimroad.hamkkae.data.response_dto.auth.VerifyIdEmailResponseDto

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
    ): Result<SendResponseDto>

    // find
    suspend fun getMyId(email:String): Result<GetMyIdResponseDto>
    suspend fun verifyIdEmail(id:String, email:String): Result<VerifyIdEmailResponseDto>
}