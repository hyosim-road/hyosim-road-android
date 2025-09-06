package com.hyosim.hamkkae.data.request_dto.plan

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseRecommendRequestDto (
    @SerialName("departureDate")
    val departureDate:String,
    @SerialName("arrivalDate")
    val arrivalDate:String,
    @SerialName("numberOfPeople")
    val numberOfPeople:Int,
    @SerialName("budgetRange")
    val budgetRange:String,
    @SerialName("travelStyle")
    val travelStyle:String,
)