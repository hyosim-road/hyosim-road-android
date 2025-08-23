package com.hyosimroad.hamkkae.data.request_dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyRequestDto (
    @SerialName("email")
    val email:String,
    @SerialName("code")
    val code:String
)