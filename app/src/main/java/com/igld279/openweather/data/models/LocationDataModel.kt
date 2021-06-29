package com.igld279.openweather.data.models

data class LocationDataModel(
        val longitude: Double = 0.0,
        val latitude: Double = 0.0,
        val cityName: String
){
    val getCityName: String
        get() = cityName
}

