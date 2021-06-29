package com.igld279.openweather.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.igld279.openweather.data.models.WeatherEntity

@Database(entities = arrayOf(WeatherEntity::class), version = 2)
@TypeConverters(RoomConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun myWeatherDao(): MyWeatherDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "weather_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}