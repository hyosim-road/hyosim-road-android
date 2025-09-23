package com.hyosim.hamkkae.data.response_dto.home

import android.os.Parcelable
import com.hyosim.hamkkae.domain.model.Info
import com.hyosim.hamkkae.domain.model.Location
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
    @SerialName("id") val id:Int,
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
            @SerialName("startTime") val startTime: String,
            @SerialName("imageUrl") val imageUrl:String?,
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
        @SerialName("pricePerNightKrw") val pricePerNightKrw: Int,
        @SerialName("imageUrl") val imageUrl:String?
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
        @SerialName("signatureMenu") val signatureMenu: String? = null,
        @SerialName("imageUrl") val imageUrl:String?
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

fun ProgressTripResponseDto.toLocationList(currentLat: Double, currentLng: Double): List<Location> {
    val locations = mutableListOf<Location>()

    var globalId = 1

    // Attraction
    itinerary.forEach { day ->
        day.attractions.forEach { attr ->
            locations.add(
                Location(
                    id = globalId++, // ✅ 전역 고유 ID
                    name = attr.name,
                    distance = calculateDistanceKm(currentLat, currentLng, attr.latitude, attr.longitude),
                    latitude = attr.latitude,
                    longitude = attr.longitude,
                    checkIn = null,
                    checkOut = null,
                    type = "attraction",
                    time = "${attr.startTime.substring(11,16)} ~ ${attr.endTime.substring(11,16)}",
                    amenityList = listOf(),
                    image = attr.imageUrl ?: ""
                )
            )
        }
    }

    // Lodging
    lodgings.forEachIndexed { index, lodge ->
        locations.add(
            Location(
                id = 1000 + index,
                name = lodge.name,
                distance = calculateDistanceKm(currentLat, currentLng, lodge.latitude, lodge.longitude),
                latitude = lodge.latitude,
                longitude = lodge.longitude,
                checkIn=lodge.checkIn,
                checkOut=lodge.checkOut,
                type = "lodgings",
                time = "${lodge.checkIn} ~ ${lodge.checkOut}",
                amenityList = lodge.amenities,
                image = ""
            )
        )
    }

    // Restaurant
    restaurants.forEachIndexed { index, restaurant ->
        locations.add(
            Location(
                id = 2000 + index,
                name = restaurant.name,
                distance = calculateDistanceKm(currentLat, currentLng, restaurant.latitude, restaurant.longitude),
                latitude = restaurant.latitude,
                longitude = restaurant.longitude,
                checkIn=null,
                checkOut=null,
                type = "restaurants",
                time = "예상비용 ${restaurant.estimatedCostPerPersonKrw}원/인",
                amenityList = listOf("대표메뉴: ${restaurant.signatureMenu ?: "정보 없음"}"),
                image = ""
            )
        )
    }

    return locations
}


fun calculateDistanceKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): String {
    val results = FloatArray(1)
    android.location.Location.distanceBetween(lat1, lon1, lat2, lon2, results)
    val meters = results[0]
    return if (meters >= 1000) {
        String.format("%.1f km", meters / 1000f)
    } else {
        String.format("%d m", meters.toInt())
    }
}