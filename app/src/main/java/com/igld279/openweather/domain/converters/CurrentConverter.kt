package com.igld279.openweather.domain.converters

import com.igld279.openweather.data.models.WeatherOneCallResponse
import com.igld279.openweather.domain.entity.CurrentWeather
import com.igld279.openweather.domain.entity.WeatherCurrent
import java.util.*
import kotlin.math.roundToInt

class CurrentConverter {

    fun getWeatherCurrent(weatherOneCallResponse: WeatherOneCallResponse): WeatherCurrent {
        return WeatherCurrent(
                weatherOneCallResponse = weatherOneCallResponse,
                currentWeather = getCurrentWeather(weatherOneCallResponse)
        )
    }

    private fun getCurrentWeather(weatherOneCallResponse: WeatherOneCallResponse): CurrentWeather {
        return CurrentWeather(
            temp = tempConverter(weatherOneCallResponse.current.temp.roundToInt()),
            icon = weatherOneCallResponse.current.weather[0].getWeatherIcon(),
            description = weatherOneCallResponse.current.weather[0].description?.capitalize(Locale.ROOT),
            feelsLike = tempConverter(weatherOneCallResponse.current.feelsLike.roundToInt())
        )
    }

    companion object{
        val instance = CurrentConverter()
    }
}