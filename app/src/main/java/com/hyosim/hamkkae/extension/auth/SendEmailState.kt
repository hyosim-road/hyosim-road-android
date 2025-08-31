package com.hyosim.hamkkae.extension.auth

sealed class SendEmailState {
    data class Success(val response: String) : SendEmailState()
    data class Error(val status: String?, val message: String?) : SendEmailState()
    object Loading : SendEmailState()
}