package com.igld279.openweather.data

import android.util.Log
import com.igld279.openweather.data.local.LocalDataSource
import com.igld279.openweather.data.location.LocationDataSource
import com.igld279.openweather.data.location.ResultLocation
import com.igld279.openweather.data.models.LocationDataModel
import com.igld279.openweather.data.models.WeatherEntity
import com.igld279.openweather.data.models.WeatherOneCallResponse
import com.igld279.openweather.data.remote.RemoteDataSource
import com.igld279.openweather.data.remote.ResultResp
import com.igld279.openweather.ui.TAG
import kotlinx.coroutines.*

class Repository(private val locationDataSource: LocationDataSource,
                 private val remoteDataSource: RemoteDataSource,
                 private val localDataSource: LocalDataSource) {

    suspend fun getMyLocation(): LocationDataModel? {
        return if (getMyLocationLast() != null){
            Log.i(TAG, "getMyLocationLast() != null")
            getMyLocationLast()
        } else if (locationDataSource.getLocationAvailable()){
            Log.i(TAG, "getMyLocationCall() != null")
            getMyLocationCall()
        } else null
    }

    private suspend fun getMyLocationLast(): LocationDataModel? {
        return when (val locationLast = locationDataSource.getLastLocation()) {
            is ResultLocation.LastSuccess -> locationLast.data
            is ResultLocation.LastError -> null
            else -> null
        }
    }

    private suspend fun getMyLocationCall(): LocationDataModel? {
        return when(val locationCall = locationDataSource.getLocationCall()){
            is ResultLocation.CallSuccess -> locationCall.data
            is ResultLocation.CallError -> null
            else -> null
        }
    }

    suspend fun getWeatherOneCallRepo(): WeatherOneCallResponse? {
            val locationDataModel = CoroutineScope(Dispatchers.Main).async { getMyLocation() }
            val resp = CoroutineScope(Dispatchers.Main).async{
                when (val response = remoteDataSource
                        .getWeatherOneCallResponse(locationDataModel.await())) {
                    is ResultResp.Success -> {
                        val weatherEntity = WeatherEntity(
                                id = 0,
                                weatherOneCallResponse = response.data
                        )
                        localDataSource.insertWeatherLocalData(weatherEntity)
                        response.data
                    }
                    is ResultResp.Error.ServerError  -> getWeatherOneCallLocal()
                    is ResultResp.Error.NetworkError  -> getWeatherOneCallLocal()
                }
            }
            return resp.await()
    }

    suspend fun getWeatherOneCallLocal(): WeatherOneCallResponse?{
        return if (localDataSource.getWeatherCountLocalData() == 0) {
            Log.i(TAG, "Count = ${localDataSource.getWeatherCountLocalData()}")
            null
        } else localDataSource.getWeatherLocalData().weatherOneCallResponse
    }
}