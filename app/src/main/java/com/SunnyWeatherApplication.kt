package com

import android.app.Application
import android.content.Context

class SunnyWeatherApplication  :Application (){

    companion object{

        lateinit var  context:Context
        const val  TOKEN="令牌值"

    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }


}