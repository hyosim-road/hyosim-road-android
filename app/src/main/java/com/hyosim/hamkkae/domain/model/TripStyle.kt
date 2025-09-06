package com.hyosim.hamkkae.domain.model

import androidx.annotation.DrawableRes

data class TripStyle(
    val id: Int,
    val name: String,
    val code:String,
    @DrawableRes val icon: Int,
)