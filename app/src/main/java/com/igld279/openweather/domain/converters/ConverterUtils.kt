package com.igld279.openweather.domain.converters

fun tempConverter(temp: Int?): String{
    if (temp == null) return ""
    if (temp > 0) {
        return "+${temp}°"
    } else {
        if (temp < 0) {
            return "${temp}°"
        }
        return "${temp}°"
    }
}