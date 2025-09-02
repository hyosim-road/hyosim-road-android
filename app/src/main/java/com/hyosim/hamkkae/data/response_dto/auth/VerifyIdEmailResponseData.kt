package com.hyosim.hamkkae.data.response_dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyIdEmailResponseData (
    @SerialName("isCorrect")
    val isCorrect:Boolean,
)