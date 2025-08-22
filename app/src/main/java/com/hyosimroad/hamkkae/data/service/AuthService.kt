package com.hyosimroad.hamkkae.data.service

import com.hyosimroad.hamkkae.data.request_dto.login.LoginRequestDto
import com.hyosimroad.hamkkae.data.request_dto.login.SignUpRequestDto
import com.hyosimroad.hamkkae.data.request_dto.login.VerifyRequestDto
import com.hyosimroad.hamkkae.data.response_dto.login.CheckIdResponseDto
import com.hyosimroad.hamkkae.data.response_dto.login.EmailResponseDto
import com.hyosimroad.hamkkae.data.response_dto.login.LoginResponseDto
import com.hyosimroad.hamkkae.data.response_dto.login.SendResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    // login
    @POST("/auth/login")
    suspend fun login(
        @Body loginRequestDto: LoginRequestDto
    ): LoginResponseDto

    // sign up
    @GET("/auth/check-id")
    suspend fun checkId(
        @Query("id") id:String
    ): CheckIdResponseDto

    @POST("/email/send")
    suspend fun send(
        @Body email:String
    ): SendResponseDto

    @POST("/email/verify")
    suspend fun verify(
        @Body verifyRequestDto: VerifyRequestDto
    ): EmailResponseDto

    @POST("/auth/join")
    suspend fun signUp(
        @Body signUpRequestDto: SignUpRequestDto
    ): EmailResponseDto

}