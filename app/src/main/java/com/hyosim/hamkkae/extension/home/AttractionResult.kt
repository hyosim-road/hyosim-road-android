package com.hyosim.hamkkae.extension.home

import com.hyosim.hamkkae.data.response_dto.home.ProgressTripResponseDto

sealed class AttractionResult {
    data class Current(val attraction: ProgressTripResponseDto.Itinerary.Attraction, val startTime:String, val endTime:String) : AttractionResult()
    data class Next(val attraction: ProgressTripResponseDto.Itinerary.Attraction, val startTime:String, val endTime:String) : AttractionResult()
    object None : AttractionResult() // 더 이상 일정 없음
}
