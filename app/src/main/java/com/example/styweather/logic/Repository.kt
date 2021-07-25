package com.example.styweather.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.example.styweather.logic.dao.PlaceDao
import com.example.styweather.logic.model.Place
import com.example.styweather.logic.model.Weather
import com.example.styweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.time.LocalDate
import kotlin.coroutines.CoroutineContext

object Repository {
    private val TAG = Repository::class.simpleName

    fun savePlace(place: Place) = PlaceDao.savePlace(place)
    fun getSavePlace() = PlaceDao.getSacePlace()
    fun isPlaceSaved() = PlaceDao.isPlaceSaved()


    fun searchPlaces(query: String) = fire(Dispatchers.IO) {

        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            placeResponse
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }


    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        Log.d(TAG, "lng--->" + lng + "----lat-->" + lat)
        coroutineScope {
            val deferredRealtime = async {
                SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferrDaily = async {
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }
            Log.d(
                TAG,
                "deferredRealtime-->" + deferredRealtime + "----deferrDaily-->" + deferrDaily
            )
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferrDaily.await()
            Log.d(
                TAG,
                "realtimeResponse-->" + realtimeResponse + "----dailyResponse-->" + dailyResponse
            )
            Log.d(
                TAG,
                "realtimeResponse-->" + realtimeResponse + "----dailyResponse-->" + dailyResponse
            )


            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }


    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}