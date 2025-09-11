package com.hyosim.hamkkae.presentation.main.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hyosim.hamkkae.data.response_dto.home.ProgressTripResponseDto
import com.hyosim.hamkkae.data.response_dto.home.toLocationList
import com.hyosim.hamkkae.domain.model.Location

class MapViewModel : ViewModel() {

    private var course: ProgressTripResponseDto? = null

    private val _locationList = MutableLiveData<List<Location>>()
    val locationList: LiveData<List<Location>> = _locationList

    var category: String? = null
        private set

    var name: String? = null
        private set

    fun setCourse(course: ProgressTripResponseDto) {
        this.course = course
    }

    fun updateLocationList(currentLat: Double, currentLng: Double) {
        course?.let {
            _locationList.value = it.toLocationList(currentLat, currentLng)
        }
    }

    fun setCategory(category: String) {
        this.category = category
    }

    fun setName(name: String) {
        this.name = name
    }
}