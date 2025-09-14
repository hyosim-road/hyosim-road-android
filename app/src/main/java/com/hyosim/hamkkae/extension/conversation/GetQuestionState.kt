package com.hyosim.hamkkae.extension.conversation

import com.hyosim.hamkkae.data.response_dto.conversation.GetQuestionResponseData

sealed class GetQuestionState {
    object Loading : GetQuestionState()
    data class Success(val question: GetQuestionResponseData) : GetQuestionState()
    data class Error(val status: String?, val message: String?) : GetQuestionState()
}