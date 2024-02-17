package com.liebersonsantos.openweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.liebersonsantos.openweatherapp.ui.feature.WeatherRoute
import com.liebersonsantos.openweatherapp.ui.theme.OpenWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenWeatherAppTheme {
               WeatherRoute()
            }
        }
    }
}