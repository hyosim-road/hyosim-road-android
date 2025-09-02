package com.hyosim.hamkkae.extension.setting

sealed class InquiryState {
    object Loading : InquiryState()
    data class Success(val message: String) : InquiryState()
    data class Error(val message: String) : InquiryState()
}