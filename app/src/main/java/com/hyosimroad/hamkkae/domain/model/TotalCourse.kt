package com.hyosimroad.hamkkae.domain.model

data class TotalCourse (
    val id:Int,
    val date:String,
    val course: List<TodaySchedule>
)