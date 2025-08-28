package com.hyosim.hamkkae.extension.auth

sealed class CodeState {
    data class Success(val success:Boolean, val email:String?) : CodeState()
    data class Error(val message: String) : CodeState()
    object Loading : CodeState()
}