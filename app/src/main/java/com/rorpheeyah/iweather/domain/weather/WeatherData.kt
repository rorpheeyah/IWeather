package com.rorpheeyah.iweather.domain.weather

import java.time.LocalDateTime

data class WeatherData(
    val time: LocalDateTime?= LocalDateTime.now(),
    val temperatureCelsius:Double ?= 0.0,
    val pressure: Double?= 0.0,
    val windSpeed: Double?= 0.0,
    val humidity: Double?= 0.0,
    val weatherType: WeatherType?= WeatherType.fromWMO(0)
)
