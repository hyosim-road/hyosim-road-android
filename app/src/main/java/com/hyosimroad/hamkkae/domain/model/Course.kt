package com.hyosimroad.hamkkae.domain.model

data class Course (
    val id:Int,
    val name:String,
    val keyword:List<String>,
    val places:List<Place>,
    val numberOfNights:String,
){
    data class Place(
        val id:Int,
        val name:String,
        val time:String,
    )
}