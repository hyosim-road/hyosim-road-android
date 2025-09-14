package com.hyosim.hamkkae.data.response_dto.conversation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostAnswerResponseData (
    @SerialName("keywords")
    val keywords:List<String>
)