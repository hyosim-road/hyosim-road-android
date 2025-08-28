package com.hyosim.hamkkae.domain.model

data class TripRecord (
    val id:Int,
    val content:String,
    val startDate:String,
    val endDate:String,
    val state:String,
    val place:String,
    val photos:List<Album.Photo>,
    val answers:List<Answer>,
    val keywords:List<String>
){
    data class Answer(
        val id:Int,
        val place:String,
        val content:String,
    )
}