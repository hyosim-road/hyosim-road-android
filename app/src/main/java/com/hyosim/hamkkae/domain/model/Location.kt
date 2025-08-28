package com.hyosim.hamkkae.domain.model

data class Location (
    val id:Int,
    val name:String,
    val distance:String,
    val latitude:Double,
    val longitude:Double,
    val type:String,
    val time:String,
    val amenityList:List<String>,
    val image:String
)