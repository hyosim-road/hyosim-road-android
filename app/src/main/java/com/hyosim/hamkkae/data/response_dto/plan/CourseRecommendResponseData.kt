package com.hyosim.hamkkae.data.response_dto.plan

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseRecommendResponseData (
    @SerialName("departureDate")
    val departureDate: String,
    @SerialName("arrivalDate")
    val arrivalDate: String,
    @SerialName("people")
    val people: Int,
    @SerialName("budgetRange")
    val budgetRange: String,
    @SerialName("travelStyle")
    val travelStyle: String,
    @SerialName("title")
    val title: String,
    @SerialName("plan")
    val plan: Plan
){
    @Serializable
    data class Plan(
        @SerialName("itinerary")
        val itinerary: List<ItineraryDay>,
        @SerialName("restaurants")
        val restaurants: List<Restaurant>,
        @SerialName("lodgings")
        val lodgings: List<Lodging>
    ){
        @Serializable
        data class ItineraryDay(
            @SerialName("day")
            val day: String,
            @SerialName("attractions")
            val attractions: List<Attraction>
        ){
            @Serializable
            data class Attraction(
                @SerialName("name")
                val name: String,
                @SerialName("latitude")
                val latitude: Double,
                @SerialName("longitude")
                val longitude: Double,
                @SerialName("startTime")
                val startTime: String, // ISO 8601 형식의 날짜-시간 문자열
                @SerialName("endTime")
                val endTime: String,   // ISO 8601 형식의 날짜-시간 문자열
                @SerialName("order")
                val order: Int,
                @SerialName("description")
                val description: String,
                @SerialName("address")
                val address: String,
                @SerialName("phone")
                val phone: String,
                @SerialName("priceKrw")
                val priceKrw: Int
            )
        }
        @Serializable
        data class Restaurant(
            @SerialName("name")
            val name: String,
            @SerialName("estimatedCostPerPersonKrw")
            val estimatedCostPerPersonKrw: Int,
            @SerialName("address")
            val address: String,
            @SerialName("latitude")
            val latitude: Double,
            @SerialName("longitude")
            val longitude: Double,
            @SerialName("description")
            val description: String,
            @SerialName("signatureMenu")
            val signatureMenu: String,
            @SerialName("phone")
            val phone: String
        )
        @Serializable
        data class Lodging(
            @SerialName("name")
            val name: String,
            @SerialName("pricePerNightKrw")
            val pricePerNightKrw: Int,
            @SerialName("address")
            val address: String,
            @SerialName("latitude")
            val latitude: Double,
            @SerialName("longitude")
            val longitude: Double,
            @SerialName("description")
            val description: String,
            @SerialName("checkIn")
            val checkIn: String, // "HH:mm:ss" 형식의 시간 문자열
            @SerialName("checkOut")
            val checkOut: String, // "HH:mm:ss" 형식의 시간 문자열
            @SerialName("amenities")
            val amenities: List<String>,
            @SerialName("phone")
            val phone: String
        )
    }
}