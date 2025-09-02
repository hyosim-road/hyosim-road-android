package com.hyosim.hamkkae.data.response_dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMyIdResponseData (
    @SerialName("id")
    val id:String
)