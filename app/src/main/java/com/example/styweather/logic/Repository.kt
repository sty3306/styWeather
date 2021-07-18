package com.example.styweather.logic

import androidx.lifecycle.liveData
import com.example.styweather.logic.model.Place
import com.example.styweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

object Repository {

fun searchPlaces(query:String)= liveData(Dispatchers.IO){
    val result=try{
        val placeResponse=SunnyWeatherNetwork.searchPlaces(query)
        if(placeResponse.status=="ok"){
            val places=placeResponse.places
            Result.success(places)
        }else{placeResponse
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }catch (e:Exception){
        Result.failure<List<Place>>(e)
    }
    emit(result)

}

}