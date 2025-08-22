package com.hyosimroad.hamkkae.extension.auth

sealed class CodeState {
    data class Success(val success:Boolean) : CodeState()
    data class Error(val message: String) : CodeState()
    object Loading : CodeState()
}