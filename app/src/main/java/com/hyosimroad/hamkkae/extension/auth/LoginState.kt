package com.hyosimroad.hamkkae.extension.auth

import com.hyosimroad.hamkkae.data.response_dto.login.LoginResponseDto

sealed class LoginState {
    data class Success(val response: LoginResponseDto) : LoginState()
    data class Error(val message: String) : LoginState()
    data object Loading : LoginState()
    data object Idle : LoginState()
}