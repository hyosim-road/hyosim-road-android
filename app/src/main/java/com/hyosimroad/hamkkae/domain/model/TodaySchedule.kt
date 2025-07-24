package com.hyosimroad.hamkkae.domain.model

data class TodaySchedule (
    val id:Int,
    val place:String,
    val keyword:String,
    val startTime:String,
    val endTime:String,
    var status:String
)