package com.hyosim.hamkkae.extension.auth

sealed class CheckIdState {
    object Loading : CheckIdState()
    data class Success(val isExist: Boolean) : CheckIdState()
    data class Error(val message: String) : CheckIdState()
}