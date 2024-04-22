package com.liebersonsantos.openweatherapp.domain.usecase

import com.liebersonsantos.openweatherapp.data.repository.WeatherRepository
import com.liebersonsantos.openweatherapp.domain.model.WeatherInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetWeatherUseCase {
    suspend fun getWeatherInfo(lat: Float, lng: Float): Flow<WeatherInfo>
}

class GetWeatherUseCaseImpl @Inject constructor(
    private val repository: WeatherRepository
): GetWeatherUseCase {
    override suspend fun getWeatherInfo(lat: Float, lng: Float): Flow<WeatherInfo> =
        repository.getWeatherData(lat = lat, lng = lng)
}