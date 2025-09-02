package com.hyosim.hamkkae.extension.auth

sealed class SendTempPwState {
    data class Success(val message: String) : SendTempPwState()
    data class Error(val message: String) : SendTempPwState()
    data object Loading : SendTempPwState()
}