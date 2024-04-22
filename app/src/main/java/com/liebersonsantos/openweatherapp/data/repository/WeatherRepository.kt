package com.liebersonsantos.openweatherapp.data.repository

import com.liebersonsantos.openweatherapp.domain.model.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
//    suspend fun getWeatherData(lat: Float, lng: Float): WeatherInfo
    suspend fun getWeatherData(lat: Float, lng: Float): Flow<WeatherInfo>
}