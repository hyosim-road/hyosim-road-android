package com.hyosim.hamkkae.data.response_dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyIdEmailResponseDto (
    @SerialName("success")
    val success:Boolean,
    @SerialName("status")
    val status:String,
    @SerialName("message")
    val message:String,
)