package com.liebersonsantos.openweatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.liebersonsantos.openweatherapp.ui.feature.screen.home.homeScreenNavigation
import com.liebersonsantos.openweatherapp.ui.feature.screen.tomorrow.tomorrowScreenNavigation

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination){
        homeScreenNavigation(navController)
        tomorrowScreenNavigation(navController)
    }
}