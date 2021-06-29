package com.igld279.openweather.domain.converters

import android.annotation.SuppressLint
import com.igld279.openweather.data.models.WeatherOneCallResponse
import com.igld279.openweather.domain.entity.DailyWeather
import com.igld279.openweather.domain.entity.WeatherDaily
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class DailyConverter {

    fun getWeatherDaily(weatherOneCallResponse: WeatherOneCallResponse): WeatherDaily {
        return WeatherDaily(
                weatherOneCallResponse = weatherOneCallResponse,
                dailyWeatherList = getDaysWeatherList(weatherOneCallResponse)
        )
    }

    private fun getDaysWeatherList(weatherOneCallResponse: WeatherOneCallResponse): List<DailyWeather> {
        val daysWeatherList = mutableListOf<DailyWeather>()
        for (item in weatherOneCallResponse.daily) {
            val daysWeather = DailyWeather(
                    data = monthDay(item.date1()),
                    dayWeek = getDayOfWeek(item.date1()),
                    icon = item.weather[0].getWeatherIcon(),
                    tempDay = tempConverter(item.temp?.day?.roundToInt()),
                    tempNight = tempConverter(item.temp?.night?.roundToInt())
            )
            daysWeatherList.add(daysWeather)
        }
        return daysWeatherList
    }

    @SuppressLint("DefaultLocale")
    private fun monthDay(date: Date): String {
        val pattern = "d MMMM"
        return date.format(pattern).capitalize()
    }

    @SuppressLint("DefaultLocale")
    private fun getDayOfWeek(date: Date): String {
        return date.format("EEEE").capitalize()
    }

    private fun Date.format(
            pattern: String = "dd.MM.yyyy HH:mm:ss",
            locale: Locale = Locale.getDefault()
    ): String {
        return SimpleDateFormat(pattern, locale).format(this)
    }

    companion object{
        val instance = DailyConverter()
    }

}