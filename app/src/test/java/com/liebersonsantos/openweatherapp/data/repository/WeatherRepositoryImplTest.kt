package com.liebersonsantos.openweatherapp.data.repository

import com.liebersonsantos.openweatherapp.data.network.NetworkResult
import com.liebersonsantos.openweatherapp.data.remote.RemoteDataSource
import com.liebersonsantos.openweatherapp.data.remote.response.WeatherDataResponse
import com.liebersonsantos.openweatherapp.domain.model.WeatherInfo
import com.liebersonsantos.openweatherapp.factory.readFile
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

class WeatherRepositoryImplTest {
    private val remoteDataSource = mockk<RemoteDataSource>()
    private val mockResponseFile = "ktor_client/weather_data_response.json".readFile()
    private val response = Json.decodeFromString<WeatherDataResponse>(mockResponseFile)
    private lateinit var repository: WeatherRepository

    @Before
    fun setup() {
        repository = WeatherRepositoryImpl(remoteDataSource = remoteDataSource)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `When getWeatherData called returns WeatherInfo`() = runBlocking {
        coEvery { remoteDataSource.getWeatherDataResponse(any(), any()) } returns NetworkResult.Success(response)

        val result = repository.getWeatherData(0f, 0f)

        result.collect {
            assertEquals(it, response.toMockDomain())
        }
    }

    @Test
    fun `When getWeatherData returns NetworkResult Error`() = runBlocking {
        coEvery { remoteDataSource.getWeatherDataResponse(any(), any()) } returns NetworkResult.Error(code = "404", errorMessage = "not found")

        repository.getWeatherData(0f, 0f).catch {
            assertEquals("not found", it.message)
        }.collect{}
    }

    @Test
    fun `When getWeatherData returns NetworkResult Exception`() = runBlocking {
        coEvery { remoteDataSource.getWeatherDataResponse(any(), any()) } returns NetworkResult.Exception(Exception("API Error"))

        val result = repository.getWeatherData(0f, 0f)

        result.catch {
            assertEquals("API Error", it.message)
        } .collect {}
    }

}

fun WeatherDataResponse.toMockDomain(): WeatherInfo =
    WeatherInfo(
        locationName = this.name,
        conditionIcon = this.weather[0].icon,
        condition = this.weather[0].description,
        temperature = this.main.temp.roundToInt(),
        dayOfWeek = LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()),
        isDay = isDayOrNight()
    )

fun isDayOrNight(): Boolean {
    val time = LocalTime.now()
    val startNight = LocalTime.of(18, 0)
    val endNight = LocalTime.of(6, 0)

    return !(time in startNight..LocalTime.MAX || time in LocalTime.MIN..endNight)
}