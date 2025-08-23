package com.hyosimroad.hamkkae.extension.auth

sealed class GetIdState {
    object Loading : GetIdState()
    data class Success(val success: Boolean) : GetIdState()
    data class Error(val message: String) : GetIdState()
}