package com.rorpheeyah.iweather.data.repository

import com.rorpheeyah.iweather.data.mapper.toWeatherInfo
import com.rorpheeyah.iweather.data.remote.weather.WeatherApi
import com.rorpheeyah.iweather.domain.repository.WeatherRepository
import com.rorpheeyah.iweather.domain.util.Resource
import com.rorpheeyah.iweather.domain.weather.WeatherInfo
import java.lang.Exception
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository{


    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        }catch (e: Exception){
            e.printStackTrace()
            Resource.Error(e.message ?: "An Unknown occurred.")
        }
    }
}