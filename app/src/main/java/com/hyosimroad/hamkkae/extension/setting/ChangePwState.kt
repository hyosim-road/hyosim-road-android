package com.hyosimroad.hamkkae.extension.setting

sealed class ChangePwState {
    object Loading : ChangePwState()
    data class Success(val status: String) : ChangePwState()
    data class Error(val message: String) : ChangePwState()
}