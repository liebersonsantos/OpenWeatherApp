package com.liebersonsantos.openweatherapp.data.repository

import com.liebersonsantos.openweatherapp.data.model.WeatherInfo
import com.liebersonsantos.openweatherapp.data.remote.RemoteDataSource
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject
import kotlin.math.roundToInt

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): WeatherRepository {
    override suspend fun getWeatherData(lat: Float, lng: Float): WeatherInfo {
        val response = remoteDataSource.getWeatherDataResponse(lat = lat, lng = lng)
        val weather = response.weather[0]

        return WeatherInfo(
            locationName = response.name,
            conditionIcon = weather.icon,
            condition = weather.description,
            temperature = response.main.temp.roundToInt(),
            dayOfWeek = LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()),
//            isDay = weather.icon.last() == 'd'
            isDay = isDayOrNight()
        )
    }
}

fun isDayOrNight(): Boolean {
    val time = LocalTime.now()
    val startNight = LocalTime.of(18, 0) // 18:00 (6:00 PM)
    val endNight = LocalTime.of(6, 0) // 06:00 (6:00 AM)

    return !(time in startNight..LocalTime.MAX || time in LocalTime.MIN..endNight)
}