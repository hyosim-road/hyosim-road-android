package com.hyosimroad.hamkkae.data.response_dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmailResponseDto (
    @SerialName("success")
    val success:Boolean,
    @SerialName("status")
    val status:String,
    @SerialName("message")
    val message:String,
    @SerialName("data")
    val data:Data?
){
    @Serializable
    data class Data(
        @SerialName("success")
        val success:Boolean,
    )
}