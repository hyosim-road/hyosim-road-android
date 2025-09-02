package com.hyosim.hamkkae.extension.setting

sealed class ResignationState {
    object Loading: ResignationState()
    object Success: ResignationState()
    data class Error(val message: String): ResignationState()
}