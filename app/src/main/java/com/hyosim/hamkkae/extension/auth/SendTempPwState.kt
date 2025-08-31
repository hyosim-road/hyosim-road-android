package com.hyosim.hamkkae.extension.auth

import com.hyosim.hamkkae.data.response_dto.auth.LoginResponseDto

sealed class SendTempPwState {
    data class Success(val message: String) : SendTempPwState()
    data class Error(val message: String) : SendTempPwState()
    data object Loading : SendTempPwState()
}