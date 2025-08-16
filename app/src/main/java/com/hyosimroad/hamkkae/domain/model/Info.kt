package com.hyosimroad.hamkkae.domain.model

data class Info (
    val id:Int,
    val image:String,
    val type:String,
    val name:String,
    val cost:String,
    val location:String,
    val description:String,
    val checkin:String?,
    val checkout:String?,
    val additionList:List<String>,
    val optionList:List<String>
)