package com.igld279.openweather.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*
import com.igld279.openweather.data.models.LocationDataModel
import com.igld279.openweather.ui.TAG
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class LocationDataSource(private val context: Context) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    private lateinit var locationCallback: LocationCallback
    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        interval = 100
        fastestInterval = 50
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        maxWaitTime= 100
    }

    @SuppressLint("MissingPermission")
    suspend fun getLastLocation(): ResultLocation<LocationDataModel> = suspendCoroutine{
        continuation ->
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val cityName = getCityName(location.latitude, location.longitude)
                    val locationDataModel = LocationDataModel(
                            latitude = location.latitude,
                            longitude = location.longitude,
                            cityName = cityName
                    )
                    Log.i(TAG, "location != null: $locationDataModel")
                    continuation.resume(ResultLocation.LastSuccess(locationDataModel))
                } else {
                    Log.i(TAG, "location == null")
                    continuation.resume(ResultLocation.LastError(""))
                }
            }
    }

    suspend fun getLocationCall(): ResultLocation<LocationDataModel> = suspendCoroutine {
        continuation ->
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                Log.i(TAG, "locationResult = $locationResult")
                locationResult ?: return continuation.resume(ResultLocation.CallError("CallError"))
                val locationCall = locationResult.locations[0]
                Log.i(TAG, "locationCall $locationCall")
                val cityName = getCityName(locationCall.latitude, locationCall.longitude)
                val locationDataModel = LocationDataModel(
                        latitude = locationCall.latitude,
                        longitude = locationCall.longitude,
                        cityName = cityName
                )
                Log.i(TAG, "locationDataModel $locationDataModel")
                stopLocationUpdates()
                continuation.resume(ResultLocation.CallSuccess(locationDataModel))
            }
        }
        startLocationUpdates()
    }


    suspend fun getLocationAvailable() : Boolean = suspendCoroutine { continuation ->
        locationCallback = object : LocationCallback() {
            override fun onLocationAvailability(var1: LocationAvailability?) {
                Log.i(TAG, "isLocationAvailable = ${var1?.isLocationAvailable}")
                val isAvailable = var1?.isLocationAvailable ?: false
                stopLocationUpdates()
                continuation.resume(isAvailable)
            }
        }
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(){
        fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper())
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }


    private fun getCityName(lat: Double, long: Double): String{
        var cityName = ""
        val geoCoder = Geocoder(context, Locale.getDefault())
        Log.i(TAG, "geoCoder.isPresent() = " + Geocoder.isPresent())
        return try {
            val address = geoCoder.getFromLocation(lat, long, 1)
            cityName = address[0].locality
            Log.i(TAG, "Your City: $cityName")
            cityName
        } catch (e: Exception){ "City..." }
    }
}

sealed class ResultLocation<out T: Any> {
    data class LastSuccess<out T : Any>(val data: T) : ResultLocation<T>()
    data class LastError(val errorText: String = "LastError") : ResultLocation<Nothing>()
    data class CallSuccess<out T : Any>(val data: T) : ResultLocation<T>()
    data class CallError(val errorText: String = "CallError") : ResultLocation<Nothing>()
}