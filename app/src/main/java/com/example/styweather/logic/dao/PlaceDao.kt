package com.example.styweather.logic.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.styweather.SunnyWeatherApplication
import com.example.styweather.logic.model.Place
import com.google.gson.Gson

object PlaceDao {


    fun savePlace(place: Place) {
        sharedPerferences().edit {
            putString("place", Gson().toJson(place))
        }


    }

    fun getSacePlace(): Place {
        val placejson = sharedPerferences().getString("place", "")
        return Gson().fromJson(placejson, Place::class.java)

    }

    fun isPlaceSaved() = sharedPerferences().contains("place")

    private fun sharedPerferences() = SunnyWeatherApplication.context.getSharedPreferences(
        "sunny_weather",
        Context.MODE_PRIVATE
    )


}