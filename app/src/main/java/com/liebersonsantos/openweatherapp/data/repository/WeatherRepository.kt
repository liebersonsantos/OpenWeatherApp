package com.liebersonsantos.openweatherapp.data.repository

import com.liebersonsantos.openweatherapp.data.model.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Float, lng: Float): WeatherInfo
}