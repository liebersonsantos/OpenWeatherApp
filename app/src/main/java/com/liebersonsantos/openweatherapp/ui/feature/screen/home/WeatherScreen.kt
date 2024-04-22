package com.liebersonsantos.openweatherapp.ui.feature.screen.home

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.liebersonsantos.openweatherapp.domain.model.WeatherInfo
import com.liebersonsantos.openweatherapp.navigation.NavigationItem
import com.liebersonsantos.openweatherapp.ui.components.ComplementComponent
import com.liebersonsantos.openweatherapp.ui.components.NextDaysItemComponent
import com.liebersonsantos.openweatherapp.ui.feature.screen.home.event.WeatherEvent
import com.liebersonsantos.openweatherapp.ui.theme.OpenWeatherAppTheme
import com.liebersonsantos.openweatherapp.utils.shimmerBrush

@Composable
fun WeatherScreen(
    navController: NavController,
    context: Context = LocalContext.current,
    viewModel: WeatherViewModel = hiltViewModel()
) {
//    var isTextVisible by remember { mutableStateOf(false) }
    val weatherInfoState by viewModel.uiState.collectAsStateWithLifecycle()
    val showShimmer = remember { mutableStateOf(true) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { result ->
            val granted = result.values.all { it }
            if (granted) {
                viewModel.dispatch(WeatherEvent.FetchWeather)
            } else {
                //
            }
        }
    )

    LaunchedEffect(Unit) {
        weatherInfoState.permissionList?.let {
            permissionLauncher.launch(it.toTypedArray())
        }
    }

    weatherInfoState.weatherInfo?.let {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            AnimatedVisibility(
//                visible = isTextVisible,
//                enter = slideInHorizontally(
//                    initialOffsetX = { fullWidth -> fullWidth },
//                    animationSpec = tween(durationMillis = 1000)
//                ),
//                exit = shrinkHorizontally() + fadeOut()
//            ) {
            TextStyleCity(weatherInfo = it, showShimmer.value)
//            }

//            AnimatedVisibility(
//                visible = isTextVisible,
//                enter = slideInHorizontally(
//                    initialOffsetX = { fullWidth -> -fullWidth },
//                    animationSpec = tween(durationMillis = 1000)
//                ),
//                exit = shrinkHorizontally() + fadeOut()
//            ) {
            Icon(context = context, weatherInfo = it, showShimmer.value)
//            }

//            AnimatedVisibility(
//                visible = isTextVisible,
//                enter = slideInHorizontally(
//                    initialOffsetX = { fullWidth -> fullWidth },
//                    animationSpec = tween(durationMillis = 1000)
//                ),
//                exit = shrinkHorizontally() + fadeOut()
//            ) {
            Text(
                text = "${it.temperature}°",
                color = Color.White,
                style = MaterialTheme.typography.displayLarge
            )
//            }

            Spacer(modifier = Modifier.height(60.dp))

            ComplementComponent()
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Today", color = Color.White)
                Text(
                    modifier = Modifier.clickable {
                        navigateTomorrow(navController = navController)
                    },
                    text = "Next Day",
                    style = TextStyle(
                        color = Color.White,
                        textDecoration = TextDecoration.Underline,
                        fontSize = 16.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(fakeList()) {
                    NextDaysItemComponent()
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }
        }
    }
}


private fun navigateTomorrow(navController: NavController) {
    navController.navigate(NavigationItem.TomorrowScreen.route)
}

private fun fakeList() = listOf("", "", "", "", "", "", "")

@Composable
fun Icon(context: Context, weatherInfo: WeatherInfo, showShimmer: Boolean) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val iconDrawableResId: Int = context.resources.getIdentifier(
            "weather_${weatherInfo.conditionIcon}",
            "drawable",
            context.packageName
        )

        Image(
            modifier = Modifier.background(shimmerBrush(showShimmer = showShimmer)),
            painter = painterResource(id = iconDrawableResId),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.background(shimmerBrush(showShimmer = showShimmer)),
            text = weatherInfo.condition,
            color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun TextStyleCity(weatherInfo: WeatherInfo, showShimmer: Boolean) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.background(shimmerBrush(showShimmer = showShimmer)),
            text = weatherInfo.locationName,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.background(shimmerBrush(showShimmer = showShimmer)),
            text = weatherInfo.dayOfWeek,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(32.dp))
    }

}

@Preview
@Composable
fun WeatherScreenPreview() {
    OpenWeatherAppTheme {
        WeatherScreen( navController = rememberNavController()
//            weatherInfo = WeatherInfo(
//                locationName = "Belo Horizonte",
//                conditionIcon = "01d",
//                condition = "Cloudy",
//                temperature = 32,
//                dayOfWeek = "Saturday",
//                isDay = true,
//            ),
//            weatherInfoState = weatherInfoState
        )
    }
}