package com.hyosim.hamkkae.extension.auth

sealed class SendEmailState {
    data class Success(val response: String) : SendEmailState()
    data class Error(val message: String) : SendEmailState()
    object Loading : SendEmailState()
}