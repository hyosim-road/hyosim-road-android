package com.hyosim.hamkkae.extension.conversation

sealed class CountState {
    data class Success (val count:Int): CountState()
    data class Error(val status:String?, val message: String?) : CountState()
    object Loading : CountState()
}