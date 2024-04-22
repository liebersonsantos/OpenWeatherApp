package com.liebersonsantos.openweatherapp.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<EVENT, STATE>: ViewModel() {
    private val _uiState: MutableStateFlow<STATE> by lazy { MutableStateFlow(initialState) }
    val uiState: StateFlow<STATE> = _uiState

    abstract val initialState: STATE

    abstract fun dispatch(event: EVENT)

    protected fun updateUiState(uiState: STATE) {
        _uiState.value = uiState
    }
}