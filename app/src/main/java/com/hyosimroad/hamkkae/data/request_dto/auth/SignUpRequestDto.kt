package com.hyosimroad.hamkkae.data.request_dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestDto (
    @SerialName("id")
    val id:String,
    @SerialName("password")
    val password:String,
    @SerialName("email")
    val email:String
)