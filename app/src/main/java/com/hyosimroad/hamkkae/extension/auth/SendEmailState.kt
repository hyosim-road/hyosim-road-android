package com.hyosimroad.hamkkae.extension.auth

import com.hyosimroad.hamkkae.data.response_dto.login.EmailResponseDto

sealed class SendEmailState {
    data class Success(val response: String) : SendEmailState()
    data class Error(val message: String) : SendEmailState()
    object Loading : SendEmailState()
}