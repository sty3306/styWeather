package com.example.styweather.ui.weather


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.styweather.logic.Repository
import com.example.styweather.logic.model.Location

class WeatherViewModel : ViewModel() {

    private val locationLiveData = MutableLiveData<Location>()
    private val TAG = WeatherViewModel::class.simpleName
    var locationLong = ""
    var locationLat = ""
    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData) {
        Log.d(TAG,"lng--->"+it.lng+"---lat-->"+it.lat)

        Repository.refreshWeather(it.lng, it.lat)
    }

    fun refreshWeather(lng: String, lat: String) {

        Log.d(TAG,"lng--->"+lng+"---lat-->"+lat)
        locationLiveData.value = Location(lng, lat)
    }


}