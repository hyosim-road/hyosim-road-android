package com.hyosim.hamkkae.data.response_dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseData (
    @SerialName("accessToken")
    val accessToken:String,
    @SerialName("refreshToken")
    val refreshToken:String
)