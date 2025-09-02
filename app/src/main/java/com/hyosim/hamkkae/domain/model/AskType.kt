package com.hyosim.hamkkae.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AskType {
    @SerialName("FUNCTION") FUNCTION,
    @SerialName("BUG")      BUG,
    @SerialName("ACCOUNT")  ACCOUNT,
    @SerialName("PAYMENT")  PAYMENT,
    @SerialName("ETC")      ETC
}