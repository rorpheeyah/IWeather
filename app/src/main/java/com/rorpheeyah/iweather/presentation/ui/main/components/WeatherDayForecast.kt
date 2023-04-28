package com.rorpheeyah.iweather.presentation.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rorpheeyah.iweather.domain.weather.WeatherData
import com.rorpheeyah.iweather.domain.weather.WeatherInfo
import com.rorpheeyah.iweather.domain.weather.WeatherType
import com.rorpheeyah.iweather.presentation.ui.main.WeatherState
import java.time.LocalDateTime

@Preview
@Composable
private fun PreviewWeatherDayForecast(){
    val weatherDataList = mutableListOf<WeatherData>()
    for (i in 0..6){
        weatherDataList.add(WeatherData(
            time = LocalDateTime.now().plusDays(i.toLong()),
            temperatureCelsius = 10.9,
            pressure = 1009.6,
            windSpeed = 15.0,
            humidity = 10.0,
            weatherType = WeatherType.fromWMO(0)
        ))
    }

    val weatherData = WeatherData(
        time = LocalDateTime.now(),
        temperatureCelsius = 10.9,
        pressure = 1009.6,
        windSpeed = 15.0,
        humidity = 10.0,
        weatherType = WeatherType.fromWMO(0)
    )

    val weatherDataPerDay  = mutableMapOf<Int, List<WeatherData>>()
    for (i in 0..5){
        weatherDataPerDay[i] = weatherDataList
    }

    val weatherInfo = WeatherInfo(
        currentWeatherData = weatherData,
        weatherDataPerDay = weatherDataPerDay
    )

    val weatherState = WeatherState(
        weatherInfo = weatherInfo,
        error = null,
        isLoading = false
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        WeatherDayForecast(weatherState = weatherState, onItemClicked = {})
    }
}

@Composable
fun WeatherDayForecast(weatherState: WeatherState, onItemClicked: (WeatherData) -> Unit){

    weatherState.weatherInfo?.weatherDataPerDay?.let { data ->
        LazyColumn(content = {
            items(data.values.toList()){weatherPerDay ->
                WeatherForecast(
                    weatherPerDay = weatherPerDay,
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.primary),
                    onItemClicked = { onItemClicked(it) }
                )
                Spacer(modifier = Modifier.padding(16.dp))
            }
        })
    }
}