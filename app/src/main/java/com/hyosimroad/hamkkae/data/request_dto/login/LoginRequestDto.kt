package com.hyosimroad.hamkkae.data.request_dto.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto (
    @SerialName("id")
    val id:String,
    @SerialName("password")
    val password:String
)