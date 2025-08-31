package com.hyosim.hamkkae.extension.auth

sealed class GetIdState {
    object Loading : GetIdState()
    data class Success(val id: String) : GetIdState()
    data class Error(val message: String) : GetIdState()
}