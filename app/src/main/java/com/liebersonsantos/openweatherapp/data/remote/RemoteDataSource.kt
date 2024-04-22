package com.liebersonsantos.openweatherapp.data.remote

import com.liebersonsantos.openweatherapp.data.network.NetworkResult
import com.liebersonsantos.openweatherapp.data.remote.response.WeatherDataResponse

interface RemoteDataSource {
    suspend fun getWeatherDataResponse(lat: Float, lng: Float): NetworkResult<WeatherDataResponse>
}