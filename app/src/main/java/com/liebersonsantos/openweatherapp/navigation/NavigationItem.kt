package com.liebersonsantos.openweatherapp.navigation

import com.liebersonsantos.openweatherapp.navigation.NavRoutes.HOME_SCREEN
import com.liebersonsantos.openweatherapp.navigation.NavRoutes.TOMORROW_SCREEN

sealed class NavigationItem(val route: String) {
    data object HomeScreen : NavigationItem(route = HOME_SCREEN)
    data object TomorrowScreen : NavigationItem(route = TOMORROW_SCREEN)
}