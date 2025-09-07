package com.hyosim.hamkkae.presentation.main.map

import com.hyosim.hamkkae.domain.model.Location
import com.kakao.vectormap.LatLng

interface MapLabelClickListener {
    fun onLabelClicked(id: Location, currentLatLng: LatLng)
}