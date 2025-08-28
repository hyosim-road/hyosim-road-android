package com.hyosim.hamkkae.domain.model

data class CourseDetail (
    val id:Int,
    val name:String,
    val description:String,
    val image:Int,
    val info:List<Info>
){
    data class Info(
        val id:Int,
        val type:String,
        val content:String,
    )
}