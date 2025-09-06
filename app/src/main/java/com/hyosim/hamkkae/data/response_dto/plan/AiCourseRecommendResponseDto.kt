package com.hyosim.hamkkae.data.response_dto.plan

import android.os.Parcelable
import com.hyosim.hamkkae.domain.model.Info
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class AiCourseRecommendResponseDto (
    @SerialName("itinerary") val itinerary: List<Itinerary>,
    @SerialName("lodgings") val lodgings: List<Lodging>,
    @SerialName("restaurants") val restaurants: List<Restaurant>
): Parcelable{

    @Parcelize
    @Serializable
    data class Itinerary(
        @SerialName("day") val day: String,
        @SerialName("attractions") val attractions: List<Attraction>
    ): Parcelable{
        @Parcelize
        @Serializable
        data class Attraction(
            @SerialName("address") val address: String,
            @SerialName("description") val description: String? = null,
            @SerialName("endTime") val endTime: String,
            @SerialName("latitude") val latitude: Double,
            @SerialName("longitude") val longitude: Double,
            @SerialName("name") val name: String,
            @SerialName("order") val order: Int,
            @SerialName("phone") val phone: String? = null,
            @SerialName("priceKrw") val priceKrw: Int,
            @SerialName("startTime") val startTime: String
        ): Parcelable
    }
    @Parcelize
    @Serializable
    data class Lodging(
        @SerialName("CheckOut") val checkOut: String,
        @SerialName("address") val address: String,
        @SerialName("amenities") val amenities: List<String>,
        @SerialName("checkIn") val checkIn: String,
        @SerialName("description") val description: String? = null,
        @SerialName("latitude") val latitude: Double,
        @SerialName("longitude") val longitude: Double,
        @SerialName("name") val name: String,
        @SerialName("phone") val phone: String? = null,
        @SerialName("pricePerNightKrw") val pricePerNightKrw: Int
    ): Parcelable

    @Parcelize
    @Serializable
    data class Restaurant(
        @SerialName("address") val address: String,
        @SerialName("description") val description: String? = null,
        @SerialName("estimatedCostPerPersonKrw") val estimatedCostPerPersonKrw: Int,
        @SerialName("latitude") val latitude: Double,
        @SerialName("longitude") val longitude: Double,
        @SerialName("name") val name: String,
        @SerialName("phone") val phone: String? = null,
        @SerialName("signatureMenu") val signatureMenu: String? = null
    ): Parcelable
}

fun AiCourseRecommendResponseDto.Restaurant.toInfo(): Info =
    Info(
        id = name,
        type = "식당",
        name = name,
        cost = "인당 ${estimatedCostPerPersonKrw}원",
        location = address,
        description = signatureMenu ?: "",
        additionList = emptyList(), // 필요하면 API에서 파싱
        optionList = emptyList()
    )

fun AiCourseRecommendResponseDto.Lodging.toInfo(): Info =
    Info(
        id = name,
        type = "숙소",
        name = name,
        cost = "${pricePerNightKrw}원 / 1박",
        location = address,
        description = description,
        additionList = emptyList(),
        optionList = emptyList(),
        checkin = checkIn,
        checkout = checkOut
    )