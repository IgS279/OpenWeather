package com.igld279.openweather.domain.converters

import com.igld279.openweather.data.models.WeatherOneCallResponse
import com.igld279.openweather.domain.entity.HoursWeather
import com.igld279.openweather.domain.entity.WeatherHourly
import kotlin.math.roundToInt

class HourlyConverter {

    fun getWeatherHourly(weatherOneCallResponse: WeatherOneCallResponse): WeatherHourly {
        return WeatherHourly(
                weatherOneCallResponse = weatherOneCallResponse,
                hoursWeatherList = getHoursList(weatherOneCallResponse)
        )
    }

    private fun getHoursList(weatherOneCallResponse: WeatherOneCallResponse): List<HoursWeather>{
        val hoursListWeather = mutableListOf<HoursWeather>()
        for (item in weatherOneCallResponse.hourly) {
            val hoursWeather = HoursWeather(
                    windSpeed = item.windSpeed.roundToInt().toString(),
                    pressure  = (item.pressure * 0.7501).roundToInt().toString(),
                    humidity  = item.humidity.toString(),
                    hour = hoursPattern(item.hoursResponse()),
                    icon = item.weather[0].getWeatherIcon(),
                    temp = tempConverter(item.temp.roundToInt())
                    )
            hoursListWeather.add(hoursWeather)
        }
        return hoursListWeather
    }

    private fun hoursPattern(hourOfDay: Int): String {
        return "$hourOfDay:00"
    }

    companion object{
        val instance = HourlyConverter()
    }
}