package com.hyosim.hamkkae.data.request_dto.setting

import com.hyosim.hamkkae.domain.model.AskType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InquiryRequestDto (
    @SerialName("inquiryType")
    val inquiryType: AskType,
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("replyEmail")
    val replyEmail: String
)