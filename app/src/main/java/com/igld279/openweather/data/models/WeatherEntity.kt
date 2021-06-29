package com.igld279.openweather.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
        @PrimaryKey//(autoGenerate = true)
        val id: Int = 0,
        @ColumnInfo(name = "weatherOneCallResponse")
        val weatherOneCallResponse: WeatherOneCallResponse
)