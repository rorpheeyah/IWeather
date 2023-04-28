package com.rorpheeyah.iweather.presentation.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rorpheeyah.iweather.domain.weather.WeatherData
import com.rorpheeyah.iweather.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Preview
@Composable
private fun PreviewWeatherHourDisplay(){
    val weatherData = WeatherData(
        time = LocalDateTime.now(),
        temperatureCelsius = 10.9,
        pressure = 1009.6,
        windSpeed = 15.0,
        humidity = 10.0,
        weatherType = WeatherType.fromWMO(0)
    )

    Box(
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.primary)
    ){
        WeatherHourDisplay(weatherData = weatherData, onItemClicked = {})
    }
}

@Composable
fun WeatherHourDisplay(
    weatherData: WeatherData,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    onItemClicked: (WeatherData) -> Unit
){

    val formattedTime = remember(weatherData) {

        if (LocalDateTime.now().dayOfYear == weatherData.time?.dayOfYear
            && LocalDateTime.now().hour == weatherData.time.hour
        ) {
            "Now"
        } else {
            weatherData.time?.format(
                DateTimeFormatter.ofPattern("hh a")
            )
        }
    }

    Card(
        colors = CardDefaults
            .cardColors(
                containerColor = if(formattedTime == "Now") MaterialTheme.colorScheme.scrim
                else MaterialTheme.colorScheme.primary
            ),
        shape = RoundedCornerShape(6.dp)
    ){
        Column(
            modifier = modifier
                .clickable { onItemClicked(weatherData) }
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formattedTime?: "",
                color = Color.LightGray
            )
            weatherData.weatherType?.iconRes?.let { painterResource(id = it) }?.let {
                Image(
                    painter = it,
                    contentDescription = null,
                    modifier = Modifier.width(40.dp)
                )
            }
            Text(
                text = "${weatherData.temperatureCelsius?.roundToInt()}°C",
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        }
    }

}