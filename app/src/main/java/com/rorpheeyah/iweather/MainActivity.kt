package com.rorpheeyah.iweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.rorpheeyah.iweather.presentation.ui.main.MainScreen
import com.rorpheeyah.iweather.presentation.ui.main.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen(viewModel = viewModel)
        }

        viewModel.loadWeatherInfo()
    }


}
