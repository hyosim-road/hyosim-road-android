package com.hyosimroad.hamkkae.data.service

import com.hyosimroad.hamkkae.data.response_dto.auth.SendResponseDto
import com.hyosimroad.hamkkae.data.response_dto.setting.CheckPasswordResponseDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SettingService {
    // change password
    @POST("/account/my-password")
    suspend fun checkPw(
        //@Header ("Authorization") accessToken:String,
        @Body password:String
    ): CheckPasswordResponseDto

    @POST("/account/reset-password")
    suspend fun resetPw(
        //@Header ("Authorization") accessToken:String,
        @Body password:String
    ): SendResponseDto
}