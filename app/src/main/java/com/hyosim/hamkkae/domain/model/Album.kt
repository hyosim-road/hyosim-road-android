package com.hyosim.hamkkae.domain.model

data class Album (
    val id:Int,
    val name:String,
    val startDate:String,
    val endDate:String,
    val place:String,
    val photos:List<Photo>,
    val keywords:List<String>
){
    data class Photo(
        val id:Int,
        val url:String,
        val place:String,
        val description:String,
        val time:String,
        val keywords:List<String>
    )
}