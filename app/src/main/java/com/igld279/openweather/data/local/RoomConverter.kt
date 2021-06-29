package com.igld279.openweather.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.igld279.openweather.data.models.WeatherOneCallResponse

class RoomConverter {

    @TypeConverter
    fun fromWeather(weatherResponse: WeatherOneCallResponse): String {
        return Gson().toJson(weatherResponse)
    }

    @TypeConverter
    fun toWeather(data: String): WeatherOneCallResponse {
        return Gson().fromJson(data, WeatherOneCallResponse::class.java)
    }
}