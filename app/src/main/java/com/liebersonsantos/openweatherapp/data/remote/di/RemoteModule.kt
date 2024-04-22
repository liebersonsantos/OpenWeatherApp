package com.liebersonsantos.openweatherapp.data.remote.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.liebersonsantos.openweatherapp.BuildConfig
import com.liebersonsantos.openweatherapp.data.network.ApiConfig
import com.liebersonsantos.openweatherapp.data.network.ApiError
import com.liebersonsantos.openweatherapp.data.network.CustomException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(HttpTimeout) {
                requestTimeoutMillis = 10_000
                socketTimeoutMillis = 10_000
                connectTimeoutMillis = 10_000
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.BODY
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                })
            }
            HttpResponseValidator {
                validateResponse { response ->
                    if (response.status != HttpStatusCode.OK) {
                        try {
                            val apiError = response.body<ApiError>()
                            throw CustomException(message = "Backend Error", apiError = apiError)
                        } catch (e: Throwable) {
                            throw Exception("${response.status}:  ${response.body<String>()}", e)
                        }
                    }

                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(application: Application): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    @Provides
    @Singleton
    fun provideApiConfig(): ApiConfig = ApiConfig(
        baseUrl = BuildConfig.BASE_URL,
        apiKey = BuildConfig.API_KEY
    )
}