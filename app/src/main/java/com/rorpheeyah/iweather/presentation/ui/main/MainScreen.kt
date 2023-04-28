package com.rorpheeyah.iweather.presentation.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rorpheeyah.iweather.presentation.theme.IWeatherTheme
import com.rorpheeyah.iweather.presentation.ui.main.components.WeatherCard
import com.rorpheeyah.iweather.presentation.ui.main.components.WeatherDayForecast

@Composable
fun MainScreen(viewModel: WeatherViewModel){

    IWeatherTheme {
        Box(modifier = Modifier.fillMaxSize()){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.primary)
            ) {
                WeatherCard(
                    weatherDataState = viewModel.weatherDataState,
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    onResetClicked = {
                        viewModel.weatherDataState = viewModel.weatherDataState.copy(
                            weatherData = viewModel.currentWeatherData,
                            isLoading = false,
                            error = null
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                WeatherDayForecast(viewModel.state, onItemClicked = {
                    viewModel.weatherDataState = viewModel.weatherDataState.copy(
                        weatherData = it,
                        isLoading = false,
                        error = null
                    )
                })
            }

            if(viewModel.state.isLoading){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }

            viewModel.state.error?.let { error ->
                Text(
                    text = error,
                    color= Color.Red,
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 50.sp
                )
            }
        }
    }
}