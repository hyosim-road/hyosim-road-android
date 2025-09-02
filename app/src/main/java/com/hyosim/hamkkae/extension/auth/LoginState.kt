package com.hyosim.hamkkae.extension.auth

import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.auth.LoginResponseData

sealed class LoginState {
    data class Success(val response: ApiResponse<LoginResponseData>) : LoginState()
    data class Error(val message: String) : LoginState()
    data object Loading : LoginState()
    data object Idle : LoginState()
}