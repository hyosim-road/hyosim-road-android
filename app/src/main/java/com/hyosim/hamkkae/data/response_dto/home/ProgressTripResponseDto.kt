package com.hyosim.hamkkae.data.response_dto.home

import android.os.Parcelable
import com.hyosim.hamkkae.domain.model.Info
import com.hyosim.hamkkae.extension.home.AttractionResult
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Parcelize
@Serializable
data class ProgressTripResponseDto (
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
        @SerialName("checkOut") val checkOut: String,
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

fun ProgressTripResponseDto.findAttraction(now: Long = System.currentTimeMillis()): AttractionResult {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA)
    val timeOnlyFormatter = SimpleDateFormat("HH:mm", Locale.KOREA)

    val todayStr = dateFormatter.format(Date(now))

    val todayItinerary = itinerary.find { it.day == todayStr }

    if (todayItinerary != null) {
        todayItinerary.attractions.forEach { attr ->
            val startDate = dateTimeFormatter.parse(attr.startTime)
            val endDate = dateTimeFormatter.parse(attr.endTime)

            if (startDate != null && endDate != null) {
                if (now in startDate.time..endDate.time) {
                    return AttractionResult.Current(
                        attr,
                        timeOnlyFormatter.format(startDate),
                        timeOnlyFormatter.format(endDate)
                    )
                }
            }
        }
        val next = todayItinerary.attractions
            .firstOrNull { dateTimeFormatter.parse(it.startTime)?.time ?: Long.MAX_VALUE > now }

        if (next != null) {
            val startDate = dateTimeFormatter.parse(next.startTime)
            val endDate = dateTimeFormatter.parse(next.endTime)
            return AttractionResult.Next(
                next,
                timeOnlyFormatter.format(startDate!!),
                timeOnlyFormatter.format(endDate!!)
            )
        }
    }

    val tomorrowItinerary = itinerary
        .sortedBy { it.day }
        .firstOrNull { it.day > todayStr }

    if (tomorrowItinerary != null && tomorrowItinerary.attractions.isNotEmpty()) {
        val first = tomorrowItinerary.attractions.first()
        val startDate = dateTimeFormatter.parse(first.startTime)
        val endDate = dateTimeFormatter.parse(first.endTime)
        return AttractionResult.Next(
            first,
            timeOnlyFormatter.format(startDate!!),
            timeOnlyFormatter.format(endDate!!)
        )
    }

    return AttractionResult.None
}