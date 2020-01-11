package com.lazday.crudroomimagekotlin.activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lazday.crudroomimagekotlin.room.DataModel
import com.lazday.crudroomimagekotlin.room.DataRepository

class MainViewModel (application: Application) : AndroidViewModel(application){

    private val repository: DataRepository = DataRepository(application)
    private val listData: LiveData<List<DataModel>> = repository.getData()

    fun getListData(): LiveData<List<DataModel>> {
        return listData
    }

}