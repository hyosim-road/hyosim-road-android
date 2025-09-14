package com.hyosim.hamkkae.presentation.main.home

fun String.noWordBreak(): String {
    return this.replace(" ", "\u00A0")
}