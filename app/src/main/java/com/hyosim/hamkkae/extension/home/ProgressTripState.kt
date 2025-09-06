package com.hyosim.hamkkae.extension.home

import com.hyosim.hamkkae.data.response_dto.home.ProgressTripResponseDto

sealed class ProgressTripState {
    object Loading : ProgressTripState()
    data class Success(val course: ProgressTripResponseDto) : ProgressTripState()
    data class Error(val status: String?, val message: String?) : ProgressTripState()
}