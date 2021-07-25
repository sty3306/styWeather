package com.example.styweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.styweather.logic.Repository
import com.example.styweather.logic.model.Place
import okhttp3.Response

class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) {
        Repository.searchPlaces(query = it)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getSavePlace() = Repository.getSavePlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()


}