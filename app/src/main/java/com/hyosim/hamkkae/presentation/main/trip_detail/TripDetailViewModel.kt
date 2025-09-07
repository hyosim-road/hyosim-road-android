package com.hyosim.hamkkae.presentation.main.trip_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyosim.hamkkae.core.BaseViewModel
import com.hyosim.hamkkae.data.response_dto.home.ProgressTripResponseDto

class TripDetailViewModel: BaseViewModel() {
    private val _course = MutableLiveData<ProgressTripResponseDto>()
    val course: LiveData<ProgressTripResponseDto> = _course

    fun setCourse(value: ProgressTripResponseDto) {
        _course.value = value
    }
}