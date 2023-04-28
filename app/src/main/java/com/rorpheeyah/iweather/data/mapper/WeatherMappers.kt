package com.rorpheeyah.iweather.data.mapper

import com.rorpheeyah.iweather.data.remote.weather.WeatherDataDto
import com.rorpheeyah.iweather.data.remote.weather.WeatherDto
import com.rorpheeyah.iweather.domain.weather.WeatherData
import com.rorpheeyah.iweather.domain.weather.WeatherInfo
import com.rorpheeyah.iweather.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val weatherData: WeatherData
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temp = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        IndexedWeatherData(
            index = index,
            weatherData = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temp,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues { it ->
        it.value.map { it.weatherData }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo{
    val weatherDataMap = weatherDataDto.toWeatherDataMap()
    val now = LocalTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if(now.minute < 30) now.hour else now.hour + 1
        it.time?.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}