package com.liebersonsantos.openweatherapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liebersonsantos.openweatherapp.navigation.NavigationItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()
    var startDestination by mutableStateOf(NavigationItem.HomeScreen.route)
        private set

    init {
        viewModelScope.launch {
            delay(1000L)
            _isReady.value = true
        }
    }
}