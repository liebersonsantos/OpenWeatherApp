package com.liebersonsantos.openweatherapp.domain

import com.liebersonsantos.openweatherapp.data.remote.response.WeatherDataResponse
import com.liebersonsantos.openweatherapp.domain.model.WeatherInfo
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

fun WeatherDataResponse.toDomain(): WeatherInfo =
    WeatherInfo(
        locationName = this.name,
        conditionIcon = this.weather[0].icon,
        condition = this.weather[0].description,
        temperature = this.main.temp.roundToInt(),
        dayOfWeek = LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()),
        isDay = isDayOrNight()
    )

fun isDayOrNight(): Boolean {
    val time = LocalTime.now()
    val startNight = LocalTime.of(18, 0)
    val endNight = LocalTime.of(6, 0)

    return !(time in startNight..LocalTime.MAX || time in LocalTime.MIN..endNight)
}