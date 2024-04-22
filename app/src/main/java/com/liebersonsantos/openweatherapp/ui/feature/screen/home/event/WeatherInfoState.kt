package com.liebersonsantos.openweatherapp.ui.feature.screen.home.event

import com.liebersonsantos.openweatherapp.domain.model.WeatherInfo

data class WeatherInfoState(
    val weatherInfo: WeatherInfo? = null,
    val destination: String? = null,
    val loading: Boolean = false,
    val message: String? = null,
    val permissionList: List<String>? = null
)
