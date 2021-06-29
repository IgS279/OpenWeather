package com.igld279.openweather.data.local

import androidx.room.*
import com.igld279.openweather.data.models.WeatherEntity

@Dao
interface MyWeatherDao {

    @Query("SELECT * FROM WeatherEntity")
    suspend fun getAllWeatherFromDB(): WeatherEntity

    @Query("SELECT count(*) FROM WeatherEntity")
    suspend fun getCountWeatherFromDB(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWeatherToDB(weatherEntity: WeatherEntity)
}