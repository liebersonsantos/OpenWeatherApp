package com.liebersonsantos.openweatherapp.data.network

import javax.inject.Inject

class ApiConfig @Inject constructor(
    var baseUrl: String,
    var apiKey: String
)
