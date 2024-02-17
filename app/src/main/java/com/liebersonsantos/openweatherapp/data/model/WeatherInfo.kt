package com.liebersonsantos.openweatherapp.data.model

data class WeatherInfo(
    val locationName: String,
    val conditionIcon: String,
    val condition: String,
    val temperature: Int,
    val dayOfWeek: String,
    val isDay: Boolean,
)
