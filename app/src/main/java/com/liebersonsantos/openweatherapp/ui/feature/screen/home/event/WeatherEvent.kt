package com.liebersonsantos.openweatherapp.ui.feature.screen.home.event

sealed class WeatherEvent {
    data object FetchWeather: WeatherEvent()
    data class Animation(val animation: Boolean = false): WeatherEvent()
    data class Navigate(val route: String): WeatherEvent()
}