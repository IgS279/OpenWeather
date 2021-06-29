package com.igld279.openweather.domain.entity

import com.igld279.openweather.data.models.WeatherOneCallResponse


data class WeatherDaily(
        val weatherOneCallResponse: WeatherOneCallResponse,
        val dailyWeatherList: List<DailyWeather>
)

data class DailyWeather(
        val data: String,
        val dayWeek: String,
        val icon: Int,
        val tempDay: String,
        val tempNight: String
)