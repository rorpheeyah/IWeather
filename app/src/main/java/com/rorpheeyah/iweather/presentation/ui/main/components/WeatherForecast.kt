package com.rorpheeyah.iweather.presentation.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rorpheeyah.iweather.domain.weather.WeatherData
import com.rorpheeyah.iweather.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Preview
@Composable
private fun PreviewWeatherForecast(){
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

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        WeatherForecast(
            weatherPerDay = weatherDataList,
            modifier = Modifier.background(color = MaterialTheme.colorScheme.primary),
            onItemClicked = {}
        )
    }
}

@Composable
fun WeatherForecast(
    weatherPerDay: List<WeatherData>,
    modifier: Modifier = Modifier,
    onItemClicked: (WeatherData) -> Unit
){
    val state = rememberLazyListState()

    weatherPerDay.let { data ->
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            val text: String? = when(data[0].time?.dayOfYear?.minus(LocalDateTime.now().dayOfYear)){
                0 -> "Today"
                1 -> "Tomorrow"
                else -> data[0].time?.format(DateTimeFormatter.ofPattern("EEEE"))
            }
            Text(
                text = text?: "",
                fontSize =20.sp,
                color = Color.White,
                modifier = modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(state = state){
                items(data) {weatherData ->
                    WeatherHourDisplay(
                        weatherData = weatherData,
                        modifier = Modifier.height(120.dp),
                        onItemClicked = { onItemClicked(it) }
                    )

                }
            }
        }
    }
}

