package com.liebersonsantos.openweatherapp.ui.feature.screen.tomorrow

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.liebersonsantos.openweatherapp.navigation.NavigationItem

fun NavGraphBuilder.tomorrowScreenNavigation(navController: NavController,) {
    composable(route = NavigationItem.TomorrowScreen.route){
        TomorrowScreen(navController = navController)
    }
}