package com.igld279.openweather.domain.entity

import com.igld279.openweather.data.models.WeatherOneCallResponse

data class WeatherHourly(
        val weatherOneCallResponse: WeatherOneCallResponse,
        val hoursWeatherList: List<HoursWeather>
)

data class HoursWeather(
        val windSpeed: String,
        val pressure : String,
        val humidity : String,
        val hour: String,
        val icon: Int,
        val temp: String
)
