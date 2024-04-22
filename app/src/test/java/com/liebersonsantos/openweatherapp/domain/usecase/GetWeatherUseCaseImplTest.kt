package com.liebersonsantos.openweatherapp.domain.usecase

import com.liebersonsantos.openweatherapp.data.network.CustomException
import com.liebersonsantos.openweatherapp.data.remote.response.WeatherDataResponse
import com.liebersonsantos.openweatherapp.data.repository.WeatherRepository
import com.liebersonsantos.openweatherapp.domain.model.WeatherInfo
import com.liebersonsantos.openweatherapp.domain.toDomain
import com.liebersonsantos.openweatherapp.factory.readFile
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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

class GetWeatherUseCaseImplTest {
    private val repository = mockk<WeatherRepository>()
    private val mockResponseFile = "ktor_client/weather_data_response.json".readFile()
    private val mockDomain = Json.decodeFromString<WeatherDataResponse>(mockResponseFile).toDomain()
    private lateinit var useCase: GetWeatherUseCase

    @Before
    fun setup() {
        useCase = GetWeatherUseCaseImpl(repository = repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `When getWeatherInfo returns WeatherInfo`() = runBlocking {
        coEvery { repository.getWeatherData(any(), any()) } returns flowOf(mockDomain)

        val result = useCase.getWeatherInfo(0f, 0f).first()

        assertEquals(mockWeatherInfo(), result)
    }

    @Test
    fun `When getWeatherInfo returns NetworkResult Error`() = runBlocking {
        coEvery { repository.getWeatherData(any(), any()) } returns flow { throw CustomException("Custom Exception") }

        val result = useCase.getWeatherInfo(0f, 0f)

        result.catch {
            assertEquals("Custom Exception", it.message)
        }.collect{}
    }
}

fun mockWeatherInfo() = WeatherInfo(
    locationName = "Mountain View",
    conditionIcon = "01d",
    condition = "céu limpo",
    temperature = 17.33.toInt(),
    dayOfWeek = LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()),
    isDay = isDayOrNight()
)

fun isDayOrNight(): Boolean {
    val time = LocalTime.now()
    val startNight = LocalTime.of(18, 0)
    val endNight = LocalTime.of(6, 0)

    return !(time in startNight..LocalTime.MAX || time in LocalTime.MIN..endNight)
}