package com.liebersonsantos.openweatherapp.data.di

import com.liebersonsantos.openweatherapp.data.location.DefaultLocationTracker
import com.liebersonsantos.openweatherapp.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Module
@InstallIn(SingletonComponent::class)
interface LocationModule {
    @Binds
    fun bindLocationTracker(defaultLocationTracker: DefaultLocationTracker): LocationTracker
}