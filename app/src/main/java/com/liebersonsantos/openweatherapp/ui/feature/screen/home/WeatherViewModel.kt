package com.liebersonsantos.openweatherapp.ui.feature.screen.home

import android.Manifest
import androidx.lifecycle.viewModelScope
import com.liebersonsantos.openweatherapp.core.BaseViewModel
import com.liebersonsantos.openweatherapp.domain.location.LocationTracker
import com.liebersonsantos.openweatherapp.domain.usecase.GetWeatherUseCase
import com.liebersonsantos.openweatherapp.ui.feature.screen.home.event.WeatherEvent
import com.liebersonsantos.openweatherapp.ui.feature.screen.home.event.WeatherInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val useCase: GetWeatherUseCase,
    private val locationTracker: LocationTracker
) : BaseViewModel<WeatherEvent, WeatherInfoState>() {

    init {
        dispatch(WeatherEvent.FetchWeather)
    }

    override val initialState: WeatherInfoState
        get() = WeatherInfoState()

    override fun dispatch(event: WeatherEvent) {
        when (event) {
            is WeatherEvent.FetchWeather -> {
                getWeatherInfo()
            }
            is WeatherEvent.Animation -> {}
            is WeatherEvent.Navigate -> {
                updateUiState(uiState = uiState.value.copy(destination = event.route))
            }
        }
    }

    private fun getWeatherInfo() =
        viewModelScope.launch {
            locationTracker.getCurrentLocation()?.let { location ->
                useCase.getWeatherInfo(location.latitude.toFloat(), location.longitude.toFloat())
                    .onStart { loading(true) }
                    .onCompletion { loading(false) }
                    .flowOn(Dispatchers.IO)
                    .catch { println(">>>>>> ${it.message}") }
                    .collect { weather ->
                        delay(2000)
                        updateUiState(uiState = uiState.value.copy(weatherInfo = weather))
                    }
            } ?: kotlin.run {
                loading(false)
                updateUiState(
                    uiState = uiState.value.copy(
                        permissionList = listOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        message = "Couldn't retrieve location. Make sure to grant permission and enable GPS")
                )
            }
        }

    private fun loading(loading: Boolean) {
        updateUiState(uiState = uiState.value.copy(loading = loading))
    }
}