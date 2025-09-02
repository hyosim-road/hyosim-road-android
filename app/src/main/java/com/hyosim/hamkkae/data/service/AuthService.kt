package com.hyosim.hamkkae.data.service

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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    // login
    @POST("/auth/login")
    suspend fun login(
        @Body loginRequestDto: LoginRequestDto
    ): ApiResponse<LoginResponseData>

    // sign up
    @GET("/auth/check-id")
    suspend fun checkId(
        @Query("id") id:String
    ): ApiResponse<CheckIdResponseData>

    @GET("/auth/check-email")
    suspend fun checkEmail(
        @Query("email") email:String
    ): ApiResponse<CheckIdResponseData>

    @POST("/email/send")
    suspend fun send(
        @Body email:String
    ): ApiResponse<Unit>

    @POST("/email/verify")
    suspend fun verify(
        @Body verifyRequestDto: VerifyRequestDto
    ): ApiResponse<EmailResponseData>

    @POST("/auth/join")
    suspend fun signUp(
        @Body signUpRequestDto: SignUpRequestDto
    ): ApiResponse<Unit>

    // find
    @GET("/auth/my-id")
    suspend fun getMyId(
        @Query("email") email:String
    ): ApiResponse<GetMyIdResponseData>

    @POST("/auth/verify-id-email")
    suspend fun verifyIdEmail(
        @Body verifyIdEmailRequestDto: VerifyIdEmailRequestDto
    ): ApiResponse<VerifyIdEmailResponseData>

    @GET("/email/temp-password")
    suspend fun sendTempPw(
        @Query("email") email:String
    ): ApiResponse<Unit>
}