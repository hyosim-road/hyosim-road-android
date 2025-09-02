package com.hyosim.hamkkae.data.service

import com.hyosim.hamkkae.data.request_dto.setting.InquiryRequestDto
import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.setting.CheckPasswordResponseData
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface SettingService {
    // change password
    @POST("/account/my-password")
    suspend fun checkPw(
        //@Header ("Authorization") accessToken:String,
        @Body password:String
    ): ApiResponse<CheckPasswordResponseData>

    @POST("/account/reset-password")
    suspend fun resetPw(
        //@Header ("Authorization") accessToken:String,
        @Body password:String
    ): ApiResponse<Unit>

    @POST("/inquiry/")
    suspend fun inquiry(
        @Body inquiryRequestDto: InquiryRequestDto
    ): ApiResponse<Int>

    // resignation
    @DELETE("/account/remove")
    suspend fun resignation(): ApiResponse<Unit>
}