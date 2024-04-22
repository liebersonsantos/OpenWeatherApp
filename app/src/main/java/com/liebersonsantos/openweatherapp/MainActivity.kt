package com.liebersonsantos.openweatherapp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.liebersonsantos.openweatherapp.domain.isDayOrNight
import com.liebersonsantos.openweatherapp.navigation.AppNavHost
import com.liebersonsantos.openweatherapp.ui.theme.BlueSky
import com.liebersonsantos.openweatherapp.ui.theme.OpenWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashAnimation()

        enableEdgeToEdge()
        setContent {
            OpenWeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = if (isDayOrNight()) BlueSky else Color.DarkGray
                ) {
                    AppNavHost(
                        navController = rememberNavController(),
                        startDestination = viewModel.startDestination
                    )
                }
            }
        }
    }

    private fun splashAnimation() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }

            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.4f,
                    0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 800L
                zoomX.doOnEnd {
                    screen.remove()
                }

                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0.0f
                )

                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 800L
                zoomY.doOnEnd {
                    screen.remove()
                }

                zoomX.start()
                zoomY.start()

                window.statusBarColor =
                    ContextCompat.getColor(applicationContext, R.color.transparent)
            }
        }
    }
}