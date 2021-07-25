package com.example.styweather

import android.app.Application
import android.content.Context

class SunnyWeatherApplication  :Application (){

    companion object{

        lateinit var  context:Context
        const val  TOKEN="f2kFp6gwlRar3krh"

    }

    override fun onCreate() {
        super.onCreate()
        context =applicationContext
    }


}