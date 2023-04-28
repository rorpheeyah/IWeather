package com.rorpheeyah.iweather.domain.repository

import com.rorpheeyah.iweather.domain.util.Resource
import com.rorpheeyah.iweather.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}