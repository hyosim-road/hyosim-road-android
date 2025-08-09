package com.hyosimroad.hamkkae.domain.model

data class Album (
    val id:Int,
    val name:String,
    val startDate:String,
    val endDate:String,
    val place:String,
    val photos:Int,
    val keywords:List<String>
)