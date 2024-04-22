package com.liebersonsantos.openweatherapp.data

import com.liebersonsantos.openweatherapp.data.network.ApiConfig
import com.liebersonsantos.openweatherapp.data.network.NetworkResult
import com.liebersonsantos.openweatherapp.data.remote.response.WeatherDataResponse
import com.liebersonsantos.openweatherapp.factory.createClient
import com.liebersonsantos.openweatherapp.factory.ktorErrorClient
import com.liebersonsantos.openweatherapp.factory.readFile
import io.ktor.http.HttpStatusCode
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class KtorRemoteDataSourceTest {
    private val apiConfig = mockk<ApiConfig>().apply {
        every { apiKey }.answers { "" }
        every { baseUrl }.answers { "" }
    }
    private lateinit var ktorRemoteDataSource: KtorRemoteDataSource
    private val mockResponseFile = "ktor_client/weather_data_response.json".readFile()

    @Test
    fun `should return success result`() = runBlocking {
        ktorRemoteDataSource = KtorRemoteDataSource(
            httpClient = createClient(
                json = mockResponseFile,
                HttpStatusCode.OK
            ), apiConfig = apiConfig
        )

        val result = ktorRemoteDataSource.getWeatherDataResponse(37.4221f, -122.0839f)

        assertTrue(result is NetworkResult.Success)
        assertEquals(
            Json.decodeFromString<WeatherDataResponse>(mockResponseFile),
            (result as NetworkResult.Success).value
        )
    }

    @Test
    fun `should return error result`() = runBlocking {
        ktorRemoteDataSource = KtorRemoteDataSource(
            httpClient = ktorErrorClient, apiConfig = apiConfig
        )

        val result = ktorRemoteDataSource.getWeatherDataResponse(37.4221f, -122.0839f)

        assertTrue(result is NetworkResult.Exception)
    }
}