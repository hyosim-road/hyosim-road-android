package com.hyosimroad.hamkkae.domain.model

import androidx.annotation.DrawableRes

data class TripStyle(
    val id: Int,
    val name: String,
    @DrawableRes val icon: Int,
)