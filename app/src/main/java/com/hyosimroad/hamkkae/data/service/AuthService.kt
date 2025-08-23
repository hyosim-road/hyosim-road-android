package com.hyosimroad.hamkkae.data.service

import com.hyosimroad.hamkkae.data.request_dto.auth.LoginRequestDto
import com.hyosimroad.hamkkae.data.request_dto.auth.SignUpRequestDto
import com.hyosimroad.hamkkae.data.request_dto.auth.VerifyRequestDto
import com.hyosimroad.hamkkae.data.response_dto.auth.CheckIdResponseDto
import com.hyosimroad.hamkkae.data.response_dto.auth.EmailResponseDto
import com.hyosimroad.hamkkae.data.response_dto.auth.GetMyIdResponseDto
import com.hyosimroad.hamkkae.data.response_dto.auth.LoginResponseDto
import com.hyosimroad.hamkkae.data.response_dto.auth.SendResponseDto
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
    ): SendResponseDto

    @GET("/account/my-id")
    suspend fun getMyId(
        @Query("email") email:String
    ): GetMyIdResponseDto

}