package com.liebersonsantos.openweatherapp.domain.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}