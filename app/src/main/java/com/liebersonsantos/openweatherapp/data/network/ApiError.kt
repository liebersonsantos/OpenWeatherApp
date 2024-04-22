package com.liebersonsantos.openweatherapp.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String
)
