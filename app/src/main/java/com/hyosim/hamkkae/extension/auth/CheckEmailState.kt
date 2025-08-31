package com.hyosim.hamkkae.extension.auth

sealed class CheckEmailState {
    object Loading : CheckEmailState()
    data class Success(val isExist: Boolean, val email:String) : CheckEmailState()
    data class Error(val status: String?, val message: String?, val email: String) : CheckEmailState()
}