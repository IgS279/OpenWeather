package com.igld279.openweather.data.remote

import com.igld279.openweather.data.models.WeatherOneCallResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class WeatherAPI {

    object ServiceBuilder {

        private val interceptor = run {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        fun getRetrofit(): Retrofit{
            return retrofit
        }
    }

interface WeatherOneCallInterface{
        @GET("data/2.5/onecall?")
        suspend fun getCurrentWeatherOneCallData(
                @Query("lat") lat: String,
                @Query("lon") lon: String,
                @Query("units") units: String,
                @Query("lang") lang: String,
                @Query("APPID") app_id: String
        )
        : Response<WeatherOneCallResponse>
    }
}