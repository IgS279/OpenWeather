package com.igld279.openweather.domain.entity

import com.igld279.openweather.data.models.WeatherOneCallResponse

data class WeatherCurrent(
        val weatherOneCallResponse: WeatherOneCallResponse,
        val currentWeather: CurrentWeather
)

data class CurrentWeather(
        val temp: String,
        val icon: Int,
        val description: String? = "",
        val feelsLike: String

)