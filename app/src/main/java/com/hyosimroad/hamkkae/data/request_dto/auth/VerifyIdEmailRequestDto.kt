package com.hyosimroad.hamkkae.data.request_dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyIdEmailRequestDto (
    @SerialName("id")
    val id:String,
    @SerialName("email")
    val email:String,
)