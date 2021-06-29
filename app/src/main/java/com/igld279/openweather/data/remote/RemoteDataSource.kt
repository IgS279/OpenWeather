package com.igld279.openweather.data.remote

import com.igld279.openweather.App
import com.igld279.openweather.data.location.ResultLocation
import com.igld279.openweather.data.models.LocationDataModel
import com.igld279.openweather.data.models.WeatherOneCallResponse

class RemoteDataSource : BaseRemote() {

    companion object{
        val instance = RemoteDataSource()
    }

    private val weatherAPI: WeatherAPI.WeatherOneCallInterface = WeatherAPI.ServiceBuilder
            .getRetrofit()
            .create(WeatherAPI.WeatherOneCallInterface::class.java)

    suspend fun getWeatherOneCallResponse(locationDataModel: LocationDataModel?)
    : ResultResp<WeatherOneCallResponse> {
        val lat = locationDataModel?.latitude.toString()
        val lon = locationDataModel?.longitude.toString()
        return safeApiResult(
                call = {weatherAPI.getCurrentWeatherOneCallData(
                        lat,
                        lon,
                        App.units,
                        App.lang,
                        App.AppId)},
                errorMessage = "NetworkError"
        )
    }
}