package com.liebersonsantos.openweatherapp.data

import com.liebersonsantos.openweatherapp.BuildConfig
import com.liebersonsantos.openweatherapp.data.remote.RemoteDataSource
import com.liebersonsantos.openweatherapp.data.remote.response.WeatherDataResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class KtorRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) : RemoteDataSource {

    private val baseUrl = BuildConfig.BASE_URL
    private val apiKey = BuildConfig.API_KEY

    override suspend fun getWeatherDataResponse(lat: Float, lng: Float): WeatherDataResponse =
        httpClient
            .get("$baseUrl/weather?lat=$lat&lon=$lng&lang=pt_br&appid=$apiKey&units=metric")
            .body()

}