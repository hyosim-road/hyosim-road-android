package com.hyosim.hamkkae.extension.auth

import com.hyosim.hamkkae.data.response_dto.auth.VerifyIdEmailResponseDto

sealed class VerifyIdEmailState {
    object Loading : VerifyIdEmailState()
    data class Success(val data: VerifyIdEmailResponseDto, val email:String) : VerifyIdEmailState()
    data class Error(val message: String) : VerifyIdEmailState()

}