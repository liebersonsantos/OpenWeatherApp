package com.liebersonsantos.openweatherapp.ui.feature.screen.tomorrow

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.liebersonsantos.openweatherapp.R
import com.liebersonsantos.openweatherapp.ui.components.TomorrowCardComponent
import com.liebersonsantos.openweatherapp.ui.theme.OpenWeatherAppTheme
import com.liebersonsantos.openweatherapp.ui.theme.Transparent

@Composable
fun TomorrowScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(top = 60.dp, start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {

        Image(
            painter = painterResource(R.drawable.baseline_arrow_back_24),
            contentDescription = "",
            alignment = Alignment.TopStart,
            modifier = Modifier.clickable {
                navController.popBackStack()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TomorrowCardComponent()
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(fakeList()) {
                DayOfWeekComponent()
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

private fun fakeList() = listOf("", "", "", "", "", "", "")

@Composable
fun DayOfWeekComponent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Transparent, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .background(color = Color.Transparent)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Sun", color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.weather_03d),
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Rain", color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.weight(6f))
            Text(
                text = "25", color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "7", color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

}

@Preview
@Composable
fun WeatherScreenPreview() {
    OpenWeatherAppTheme {
        TomorrowScreen(navController = rememberNavController())
    }
}