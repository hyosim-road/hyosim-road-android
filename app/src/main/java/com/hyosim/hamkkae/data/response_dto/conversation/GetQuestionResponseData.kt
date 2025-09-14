package com.hyosim.hamkkae.data.response_dto.conversation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetQuestionResponseData (
    @SerialName("id")
    val id:Int,
    @SerialName("content")
    val content:String
)