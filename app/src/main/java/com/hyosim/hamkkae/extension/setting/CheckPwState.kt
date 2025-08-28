package com.hyosim.hamkkae.extension.setting

sealed class CheckPwState {
    data class Success(val isCorrect:Boolean) : CheckPwState()
    data class Error(val message: String) : CheckPwState()
    object Loading : CheckPwState()
}