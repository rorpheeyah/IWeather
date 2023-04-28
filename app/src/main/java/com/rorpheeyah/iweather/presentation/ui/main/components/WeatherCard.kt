package com.rorpheeyah.iweather.presentation.ui.main.components

import androidx.annotation.ColorInt
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rorpheeyah.iweather.R
import com.rorpheeyah.iweather.domain.weather.WeatherData
import com.rorpheeyah.iweather.domain.weather.WeatherType
import com.rorpheeyah.iweather.presentation.ui.main.WeatherDataState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Preview(showBackground = true)
@Composable
private fun PreviewWeatherCard(){
    val weatherData = WeatherData(
        time = LocalDateTime.now(),
        temperatureCelsius = 10.9,
        pressure = 1009.6,
        windSpeed = 15.0,
        humidity = 10.0,
        weatherType = WeatherType.fromWMO(0)
    )

    val weatherDataState = WeatherDataState(
        weatherData = weatherData,
        isLoading = true
    )

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        WeatherCard(
            weatherDataState = weatherDataState,
            backgroundColor = MaterialTheme.colorScheme.primary,
            onResetClicked = {}
        )
    }
}

@Composable
fun WeatherCard(
    weatherDataState: WeatherDataState,
    @ColorInt backgroundColor: Color,
    modifier: Modifier = Modifier,
    onResetClicked: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 200.dp),
    ){

        Card(
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            shape = RoundedCornerShape(4.dp),
            modifier = modifier.padding(14.dp)
        ) {

            weatherDataState.weatherData?.let { data ->
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        data.time?.let {
                            Text(
                                text = if (LocalDateTime.now().dayOfYear == it.dayOfYear)
                                    "Today ${it.format(DateTimeFormatter.ofPattern("E hh a"))}"
                                else it.format(DateTimeFormatter.ofPattern("E hh a")),
                                textAlign = TextAlign.Right,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.padding(2.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ic_reset),
                            contentDescription = null,
                            modifier = modifier
                                .size(16.dp)
                                .clickable{
                                    onResetClicked()
                                },
                            contentScale = ContentScale.Fit,
                            colorFilter = ColorFilter.tint(color = Color.White)
                        )
                    }

                    Spacer(modifier = Modifier.padding(16.dp))

                    data.weatherType?.iconRes?.let { painterResource(id = it) }?.let {
                        Image(
                            painter = it,
                            contentDescription = "weather type",
                            modifier = modifier.size(100.dp)
                        )
                    }

                    Spacer(modifier = Modifier.padding(16.dp))

                    data.temperatureCelsius?.let {
                        Text(
                            text = "${it.roundToInt()}°C",
                            color = Color.White,
                            fontSize = 50.sp
                        )
                    }

                    Spacer(modifier = Modifier.padding(16.dp))

                    data.weatherType?.weatherDesc?.let {
                        Text(
                            text = it,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        data.pressure?.roundToInt()?.let {
                            WeatherDataDisplay(
                                value = it,
                                unit = "hpa",
                                icon = ImageVector.vectorResource(id = R.drawable.ic_pressure)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        data.humidity?.roundToInt()?.let {
                            WeatherDataDisplay(
                                value = it,
                                unit = "%",
                                icon = ImageVector.vectorResource(id = R.drawable.ic_drop)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        data.windSpeed?.roundToInt()?.let {
                            WeatherDataDisplay(
                                value = it,
                                unit = "km/h",
                                icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                                iconTint = Color.White,
                                textStyle = TextStyle(color = Color.White)
                            )
                        }
                    }
                }
            }
        }
    }

}