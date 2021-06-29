package com.igld279.openweather.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.igld279.openweather.App
import com.igld279.openweather.data.Repository
import com.igld279.openweather.data.location.ResultLocation
import com.igld279.openweather.data.models.LocationDataModel
import com.igld279.openweather.data.models.WeatherOneCallResponse
import com.igld279.openweather.domain.converters.CurrentConverter
import com.igld279.openweather.domain.converters.DailyConverter
import com.igld279.openweather.domain.converters.HourlyConverter
import com.igld279.openweather.domain.entity.WeatherCurrent
import com.igld279.openweather.domain.entity.WeatherDaily
import com.igld279.openweather.domain.entity.WeatherHourly
import kotlinx.coroutines.launch

class MainViewModel(application: Application,
                    private val repository: Repository,
                    private val weatherCurrentConverter: CurrentConverter,
                    private val weatherHourlyConverter: HourlyConverter,
                    private val weatherDailyConverter: DailyConverter
                    )
    : AndroidViewModel(application) {

    private var _myCityName = MutableLiveData<String>()
    val myCityName: LiveData<String>
        get() = _myCityName

    private var _weatherCurrent = MutableLiveData<WeatherCurrent>()
    val weatherCurrent: LiveData<WeatherCurrent>
        get() = _weatherCurrent

    private var _weatherHourly = MutableLiveData<WeatherHourly>()
    val weatherHourly: LiveData<WeatherHourly>
        get() = _weatherHourly

    private var _weatherDaily = MutableLiveData<WeatherDaily>()
    val weatherDaily: LiveData<WeatherDaily>
        get() = _weatherDaily

    private var _hasNetwork = MutableLiveData<Boolean>()
    val hasNetwork: LiveData<Boolean>
        get() = _hasNetwork

    val isLoading = ObservableBoolean()

    init {
        startGetDataWeather()
    }

    private fun startGetDataWeather(){
        suspend fun postLiveData(response: WeatherOneCallResponse){
            _weatherCurrent.postValue(weatherCurrentConverter.getWeatherCurrent(response))
            _weatherHourly.postValue(weatherHourlyConverter.getWeatherHourly(response))
            _weatherDaily.postValue(weatherDailyConverter.getWeatherDaily(response))
            _myCityName.postValue(repository.getMyLocation()?.getCityName)
            onSwipeReady()
        }
        viewModelScope.launch {
            if(checkNetwork()) {
                val response = repository.getWeatherOneCallRepo()
                if (response != null) {
                    postLiveData(response)
                }
                _hasNetwork.value = true
            } else {
                val response = repository.getWeatherOneCallLocal()
                if (response != null) {
                    postLiveData(response)
                }
                _hasNetwork.value = false
            }
        }
    }

    fun onSwipeRefresh() {
        isLoading.set(true)
        startGetDataWeather()
    }

    private fun onSwipeReady() = isLoading.set(false)

    @Suppress("DEPRECATION")
    private fun checkNetwork(): Boolean {
        val cm = getApplication<App>()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = cm.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting == true
    }
}