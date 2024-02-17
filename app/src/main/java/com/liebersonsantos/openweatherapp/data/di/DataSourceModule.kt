package com.liebersonsantos.openweatherapp.data.di

import com.liebersonsantos.openweatherapp.data.KtorRemoteDataSource
import com.liebersonsantos.openweatherapp.data.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindRemoteDataSource(remoteDataSource: KtorRemoteDataSource): RemoteDataSource
}