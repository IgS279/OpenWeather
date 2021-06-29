package com.igld279.openweather.data.local

import com.igld279.openweather.data.models.WeatherEntity

class LocalDataSource(private val myWeatherDao: MyWeatherDao) {

    suspend fun insertWeatherLocalData(weatherEntity: WeatherEntity) {
        myWeatherDao.insertAllWeatherToDB(weatherEntity)
    }

    suspend fun getWeatherLocalData(): WeatherEntity{
        return myWeatherDao.getAllWeatherFromDB()
    }

    suspend fun getWeatherCountLocalData(): Int{
        return myWeatherDao.getCountWeatherFromDB()
    }
}