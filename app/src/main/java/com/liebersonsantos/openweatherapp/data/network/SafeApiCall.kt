package com.liebersonsantos.openweatherapp.data.network

suspend fun <T: Any> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> =
    try {
        NetworkResult.Success(value = apiCall.invoke())
    } catch (e: Exception) {
        NetworkResult.Exception(e)
    } catch (e: CustomException) {
        mapCustomException(e)
    }

class CustomException(
    message: String? = null,
    val apiError: ApiError? = null
): Exception(message)

fun <T> mapCustomException(e: CustomException): NetworkResult<T> =
    NetworkResult.Error(code = e.apiError?.code ?: "0", errorMessage = e.apiError?.message ?: "Erro desconhecido")