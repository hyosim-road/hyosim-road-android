package com.hyosim.hamkkae.presentation.main.map

import com.kakao.vectormap.LatLng

interface MapLabelClickListener {
    fun onLabelClicked(id:Int, currentLatLng: LatLng)
}