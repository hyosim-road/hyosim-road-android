package com.hyosim.hamkkae.domain.model

data class SignupRequest(
    val id: String,
    val pw: String,
    val email: String,
    val emailAuthCode: String
)