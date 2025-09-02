package com.hyosim.hamkkae.data.datasource

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

interface AuthDataSource {
    // login
    suspend fun login(loginRequestDto: LoginRequestDto): ApiResponse<LoginResponseData>

    // sign up
    suspend fun checkId(id: String): ApiResponse<CheckIdResponseData>
    suspend fun checkEmail(email: String): ApiResponse<CheckIdResponseData>
    suspend fun send(email: String): ApiResponse<Unit>
    suspend fun verify(verifyRequestDto: VerifyRequestDto): ApiResponse<EmailResponseData>
    suspend fun signUp(signUpRequestDto: SignUpRequestDto): ApiResponse<Unit>

    // find
    suspend fun getMyId(email: String): ApiResponse<GetMyIdResponseData>
    suspend fun verifyIdEmail(verifyIdEmailRequestDto: VerifyIdEmailRequestDto): ApiResponse<VerifyIdEmailResponseData>
    suspend fun sendTempPw(email:String): ApiResponse<Unit>
}