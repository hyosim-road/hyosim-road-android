package com.hyosim.hamkkae.data.request_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class PostAnswerRequestDto (
    @SerialName("questionId")
    val questionId:Int,
    @SerialName("content")
    val content:String
)