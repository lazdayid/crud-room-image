package com.lazday.crudroomimagekotlin

import android.app.Application
import com.lazday.crudroomimagekotlin.room.DataRepository

class RoomApp : Application() {

    companion object {
        var repository: DataRepository? = null
    }

    override fun onCreate() {
        super.onCreate()
        repository = DataRepository(this)
    }
}