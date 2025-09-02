package com.hyosim.hamkkae.data.response_dto.setting

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckPasswordResponseData (
    @SerialName("isCorrect")
    val isCorrect:Boolean
)