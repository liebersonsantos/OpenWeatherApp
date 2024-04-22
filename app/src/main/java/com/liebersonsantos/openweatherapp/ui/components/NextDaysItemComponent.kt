package com.liebersonsantos.openweatherapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.liebersonsantos.openweatherapp.R
import com.liebersonsantos.openweatherapp.ui.theme.OpenWeatherAppTheme
import com.liebersonsantos.openweatherapp.ui.theme.Transparent

@Composable
fun NextDaysItemComponent() {
    Column(
        modifier = Modifier
            .background(Transparent, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "7 am", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Image(
            painter = painterResource(
                id = R.drawable.weather_01d),
            contentDescription = "Icon",
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "25")
    }
}

@Composable
@Preview(showBackground = true)
fun NextDaysComponentPreview() {
    OpenWeatherAppTheme {
        NextDaysItemComponent()
    }
}