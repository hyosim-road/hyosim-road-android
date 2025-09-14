package com.hyosim.hamkkae.data.response_dto.conversation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAnswersResponseData (
    @SerialName("tripId")
    val tripId: Int,
    @SerialName("questionId")
    val questionId: Int,
    @SerialName("questionContent")
    val questionContent: String,
    @SerialName("answerId")
    val answerId: Int?,
    @SerialName("answerContent")
    val answerContent: String?,
    @SerialName("tags")
    val tags: List<String>
)