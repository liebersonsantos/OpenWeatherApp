package com.liebersonsantos.openweatherapp.data.repository

import com.liebersonsantos.openweatherapp.data.network.toFlow
import com.liebersonsantos.openweatherapp.data.remote.RemoteDataSource
import com.liebersonsantos.openweatherapp.domain.model.WeatherInfo
import com.liebersonsantos.openweatherapp.domain.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : WeatherRepository {
    override suspend fun getWeatherData(lat: Float, lng: Float): Flow<WeatherInfo> =
        remoteDataSource.getWeatherDataResponse(lat = lat, lng = lng).toFlow().map {
            it.toDomain()
        }
}
