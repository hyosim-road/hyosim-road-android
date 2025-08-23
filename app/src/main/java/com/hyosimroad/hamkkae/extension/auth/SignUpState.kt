package com.hyosimroad.hamkkae.extension.auth

sealed class SignUpState {
    object Loading : SignUpState()
    data class Success(val status: String) : SignUpState()
    data class Error(val message: String) : SignUpState()
}