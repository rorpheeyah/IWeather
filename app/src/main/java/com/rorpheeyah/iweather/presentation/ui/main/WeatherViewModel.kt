package com.rorpheeyah.iweather.presentation.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rorpheeyah.iweather.domain.repository.WeatherRepository
import com.rorpheeyah.iweather.domain.util.Resource
import com.rorpheeyah.iweather.domain.weather.WeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
    ): ViewModel(){

    var state by mutableStateOf(WeatherState())
    var weatherDataState by mutableStateOf(WeatherDataState())
    var currentWeatherData by mutableStateOf(WeatherData())

    fun loadWeatherInfo(){
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            weatherDataState = weatherDataState.copy(isLoading = true, error = null)

            when(val result = repository.getWeatherData(35.179554, 129.075638)){
                is Resource.Success ->{
                    state = state.copy(
                        weatherInfo = result.data,
                        isLoading = false,
                        error = null
                    )

                    weatherDataState = weatherDataState.copy(
                        weatherData = state.weatherInfo?.currentWeatherData,
                        isLoading = false,
                        error = null
                    )

                    state.weatherInfo?.currentWeatherData?.let {
                        currentWeatherData = it
                    }

                }

                is Resource.Error ->{
                    state = state.copy(
                        weatherInfo = null,
                        isLoading = true,
                        error = result.message
                    )
                }
            }
        }
    }

}