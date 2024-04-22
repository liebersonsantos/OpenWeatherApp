package com.liebersonsantos.openweatherapp.data.di

import com.liebersonsantos.openweatherapp.domain.usecase.GetWeatherUseCase
import com.liebersonsantos.openweatherapp.domain.usecase.GetWeatherUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {
    @Binds
    fun bindGetWeatherUseCase(useCaseImpl: GetWeatherUseCaseImpl): GetWeatherUseCase
}