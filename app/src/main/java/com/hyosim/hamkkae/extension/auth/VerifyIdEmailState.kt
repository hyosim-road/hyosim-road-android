package com.hyosim.hamkkae.extension.auth

import com.hyosim.hamkkae.data.response_dto.ApiResponse
import com.hyosim.hamkkae.data.response_dto.auth.VerifyIdEmailResponseData

sealed class VerifyIdEmailState {
    object Loading : VerifyIdEmailState()
    data class Success(val data: ApiResponse<VerifyIdEmailResponseData>, val email: String) : VerifyIdEmailState()
    data class Error(val message: String) : VerifyIdEmailState()

}