package com.hyosim.hamkkae.extension.conversation

import com.hyosim.hamkkae.data.response_dto.conversation.PostAnswerResponseData

sealed class PostAnswerState {
    object Loading : PostAnswerState()
    data class Success(val question: PostAnswerResponseData) : PostAnswerState()
    data class Error(val status: String?, val message: String?) : PostAnswerState()
}