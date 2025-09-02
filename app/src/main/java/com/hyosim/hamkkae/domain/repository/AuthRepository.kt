package com.hyosim.hamkkae.domain.repository

import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.auth.CheckIdResponseData
import com.hyosim.hamkkae.data.response_dto.auth.EmailResponseData
import com.hyosim.hamkkae.data.response_dto.auth.GetMyIdResponseData
import com.hyosim.hamkkae.data.response_dto.auth.LoginResponseData
import com.hyosim.hamkkae.data.response_dto.auth.VerifyIdEmailResponseData

interface AuthRepository {
    // login
    suspend fun login(
        email: String,
        pw: String
    ): Result<ApiResponse<LoginResponseData>>

    // sign up
    suspend fun checkId(id:String):Result< ApiResponse<CheckIdResponseData>>
    suspend fun checkEmail(email:String):Result< ApiResponse<CheckIdResponseData>>
    suspend fun send(
        email: String
    ): Result<ApiResponse<Unit>>
    suspend fun verify(email:String, code:String): Result< ApiResponse<EmailResponseData>>
    suspend fun signUp(
        id: String,
        pw: String,
        email: String
    ): Result<ApiResponse<Unit>>

    // find
    suspend fun getMyId(email:String): Result< ApiResponse<GetMyIdResponseData>>
    suspend fun verifyIdEmail(id:String, email:String): Result<ApiResponse<VerifyIdEmailResponseData>>
    suspend fun sendTempPw(email:String): Result<ApiResponse<Unit>>
}