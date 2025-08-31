package com.hyosim.hamkkae.data.service

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

    @GET("/auth/check-email")
    suspend fun checkEmail(
        @Query("email") email:String
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

    // find
    @GET("/auth/my-id")
    suspend fun getMyId(
        @Query("email") email:String
    ): GetMyIdResponseDto

    @POST("/auth/verify-id-email")
    suspend fun verifyIdEmail(
        @Body verifyIdEmailRequestDto: VerifyIdEmailRequestDto
    ): VerifyIdEmailResponseDto

    @GET("/email/temp-password")
    suspend fun sendTempPw(
        @Query("email") email:String
    ): SendResponseDto

}