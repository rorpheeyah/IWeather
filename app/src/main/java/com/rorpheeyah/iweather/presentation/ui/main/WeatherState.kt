package com.rorpheeyah.iweather.presentation.ui.main

import com.rorpheeyah.iweather.domain.weather.WeatherData
import com.rorpheeyah.iweather.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo ?= null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class WeatherDataState(
    val weatherData: WeatherData ?= null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false
)