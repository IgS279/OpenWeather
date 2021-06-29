package com.igld279.openweather.data.models

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.igld279.openweather.R
import com.igld279.openweather.ui.TAG
import java.util.*

data class WeatherOneCallResponse(
        @SerializedName("lat")
        val lat: Double = 0.toDouble(),
        @SerializedName("lon")
        val lon: Double = 0.toDouble(),
        @SerializedName("timezone")
        val timezone: String? = null,
        @SerializedName("timezone_offset")
        val timezoneOffset: Long = 0,
        @SerializedName("current")
        val current: Current,
        @SerializedName("minutely")
        val minutely: List<Minutely>,
        @SerializedName("hourly")
        val hourly: List<Hourly>,
        @SerializedName("daily")
        val daily: List<Daily>,
        @SerializedName("alerts")
        val alerts: List<Alert>? = null
)

data class Alert (
        @SerializedName("sender_name")
        val senderName: String? = null,
        @SerializedName("event")
        val event: String? = null,
        @SerializedName("start")
        val start: Long = 0,
        @SerializedName("end")
        val end: Long = 0,
        @SerializedName("description")
        val description: String? = null
)

data class Current (
        @SerializedName("dt")
        val dt: Long = 0,
        @SerializedName("sunrise")
        val sunrise: Long = 0,
        @SerializedName("sunset")
        val sunset: Long = 0,
        @SerializedName("temp")
        val temp: Double = 0.toDouble(),
        @SerializedName("feels_like")
        val feelsLike: Double = 0.toDouble(),
        @SerializedName("pressure")
        val pressure: Long = 0,
        @SerializedName("humidity")
        val humidity: Long = 0,
        @SerializedName("dew_point")
        val dewPoint: Double = 0.toDouble(),
        @SerializedName("uvi")
        val uvi:  Double = 0.toDouble(),
        @SerializedName("clouds")
        val clouds: Long = 0,
        @SerializedName("visibility")
        val visibility: Long = 0,
        @SerializedName("wind_speed")
        val windSpeed: Double = 0.toDouble(),
        @SerializedName("wind_deg")
        val windDeg: Long = 0,
        @SerializedName("weather")
        val weather: List<Weather>,
        @SerializedName("pop")
        val pop: Double = 0.toDouble()
)

data class Weather (
        @SerializedName("id")
        val id: Long =0,
        @SerializedName("main")
        val main: String? = null,
        @SerializedName("description")
        val description: String? = null,
        @SerializedName("icon")
        val icon: String? = null
) {
        fun getWeatherIcon(): Int{
                return when(icon){
                        "01d" -> R.drawable.ic_01d2x
                        "02d" -> R.drawable.ic_02d2x
                        "03d" -> R.drawable.ic_03d2x
                        "04d" -> R.drawable.ic_04d2x
                        "09d" -> R.drawable.ic_09d2x
                        "10d" -> R.drawable.ic_10d2x
                        "11d" -> R.drawable.ic_11d2x
                        "13d" -> R.drawable.ic_13d2x
                        "50d" -> R.drawable.ic_50d2x

                        "01n" -> R.drawable.ic_01n2x
                        "02n" -> R.drawable.ic_02n2x
                        "03n" -> R.drawable.ic_03n2x
                        "04n" -> R.drawable.ic_04n2x
                        "09n" -> R.drawable.ic_09n2x
                        "10n" -> R.drawable.ic_10n2x
                        "11n" -> R.drawable.ic_11n2x
                        "13n" -> R.drawable.ic_13n2x
                        "50n" -> R.drawable.ic_50n2x
                        else -> {
                                Log.i(TAG, "default icon (else)")
                                R.drawable.ic_03d2x
                        }
                }
        }
}

data class Hourly(
        @SerializedName("clouds")
        val clouds: Int,
        @SerializedName("dew_point")
        val dewPoint: Double,
        @SerializedName("dt")
        val dt: Int,
        @SerializedName("feels_like")
        val feelsLike: Double,
        @SerializedName("humidity")
        val humidity: Int,
        @SerializedName("pressure")
        val pressure: Int,
        @SerializedName("temp")
        val temp: Double,
        @SerializedName("weather")
        val weather: List<Weather>,
        @SerializedName("wind_deg")
        val windDeg: Int,
        @SerializedName("wind_speed")
        val windSpeed: Double
){
        fun hoursResponse(): Int {
                val calendar = Calendar.getInstance()
                val localTime = (dt * 1000L)
                calendar.time = Date(localTime)
                return calendar.get(Calendar.HOUR_OF_DAY)
        }
}

data class Daily (
        @SerializedName("dt")
        val dt: Long = 0,
        @SerializedName("sunrise")
        val sunrise: Long = 0,
        @SerializedName("sunset")
        val sunset: Long = 0,
        @SerializedName("temp")
        val temp: Temp? = null,
        @SerializedName("feels_like")
        val feelsLike: FeelsLike? = null,
        @SerializedName("pressure")
        val pressure: Long = 0,
        @SerializedName("humidity")
        val humidity: Long = 0,
        @SerializedName("dew_point")
        val dewPoint: Double = 0.toDouble(),
        @SerializedName("wind_speed")
        val windSpeed: Double = 0.toDouble(),
        @SerializedName("wind_deg")
        val windDeg: Long = 0,
        @SerializedName("weather")
        val weather: List<Weather>,
        @SerializedName("clouds")
        val clouds: Long = 0,
        @SerializedName("pop")
        val pop: Double = 0.toDouble(),
        @SerializedName("uvi")
        val uvi: Double = 0.toDouble()
){
        private fun calendarTime(): Calendar {
                val tz = TimeZone.getDefault()
                val offsetFromUtc = tz.getOffset(System.currentTimeMillis())
                val time = (dt * 1000L) - offsetFromUtc
                val calendar = Calendar.getInstance()
                calendar.time = Date(time)
                return calendar
        }

        fun date1(): Date {
                return calendarTime().time
        }
}

data class FeelsLike (
        @SerializedName("day")
        val day: Double = 0.toDouble(),
        @SerializedName("night")
        val night: Double = 0.toDouble(),
        @SerializedName("eve")
        val eve: Double = 0.toDouble(),
        @SerializedName("morn")
        val morn: Double = 0.toDouble()
)

data class Temp (
        @SerializedName("day")
        val day: Double = 0.toDouble(),
        @SerializedName("min")
        val min: Double = 0.toDouble(),
        @SerializedName("max")
        val max: Double = 0.toDouble(),
        @SerializedName("night")
        val night: Double = 0.toDouble(),
        @SerializedName("eve")
        val eve: Double = 0.toDouble(),
        @SerializedName("morn")
        val morn: Double = 0.toDouble()
)

data class Minutely (
        @SerializedName("dt")
        val dt: Long = 0,
        @SerializedName("precipitation")
        val precipitation: Double = 0.toDouble()
)