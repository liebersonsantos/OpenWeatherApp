package com.liebersonsantos.openweatherapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.liebersonsantos.openweatherapp.ui.theme.OpenWeatherAppTheme
import com.liebersonsantos.openweatherapp.ui.theme.Transparent

@Composable
fun ComplementComponent() {
    Box(
        modifier = Modifier.background(Transparent, RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 48.dp, vertical = 12.dp)
                .background(color = Color.Transparent)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ComplementItem()
            ComplementItem()
            ComplementItem()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ComplementComponentPreview() {
    OpenWeatherAppTheme {
        ComplementComponent()
    }
}