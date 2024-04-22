package com.liebersonsantos.openweatherapp.data.di

import com.liebersonsantos.openweatherapp.data.repository.WeatherRepository
import com.liebersonsantos.openweatherapp.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun bindWeatherRepository(repositoryImpl: WeatherRepositoryImpl): WeatherRepository
}
