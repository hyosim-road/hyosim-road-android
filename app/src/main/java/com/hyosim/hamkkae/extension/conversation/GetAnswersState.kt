package com.hyosim.hamkkae.extension.conversation

import com.hyosim.hamkkae.data.response_dto.conversation.GetAnswersResponseData
import com.hyosim.hamkkae.data.response_dto.conversation.PostAnswerResponseData

sealed class GetAnswersState {
    object Loading : GetAnswersState()
    data class Success(val question: List<GetAnswersResponseData>) : GetAnswersState()
    data class Error(val status: String?, val message: String?) : GetAnswersState()
}