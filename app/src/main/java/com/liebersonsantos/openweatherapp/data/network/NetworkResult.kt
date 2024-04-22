package com.liebersonsantos.openweatherapp.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

sealed class NetworkResult<out T> {
    data class Success<out T>(val value: T): NetworkResult<T>()
    data class Error(val code: String, val errorMessage: String?): NetworkResult<Nothing>()
    data class Exception(val exception: Throwable): NetworkResult<Nothing>()
}

fun <T> NetworkResult<T>.toFlow(): Flow<T> {
    val response = this
    return flow {
        when(response) {
            is NetworkResult.Success -> {
                emit(response.value)
            }
            is NetworkResult.Error -> {
                throw CustomException(message = response.errorMessage)
            }
            is NetworkResult.Exception -> {
                throw CustomException(message = response.exception.message)
            }
        }
    }
}