package com.hyosimroad.hamkkae.domain.model

data class Answer(
    val id:Int,
    val place:String,
    val keywords:List<String>,
    val content:String,
)
