package com.lazday.crudroomimagekotlin.room

import android.app.Application
import androidx.lifecycle.LiveData
import com.lazday.crudroomimagekotlin.room.DataDao
import com.lazday.crudroomimagekotlin.room.DataModel
import com.lazday.crudroomimagekotlin.room.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DataRepository (application: Application) {

    private val dataDao: DataDao
//    private val listData: List<DataModel>
    private val listData: LiveData<List<DataModel>>

    init {
        val database =
            MyDatabase.getInstance(
                application.applicationContext
            )
        dataDao = database!!.dataDao()
        listData = dataDao.getData()
    }

    fun createData(dataModel: DataModel) = runBlocking {
        this.launch(Dispatchers.IO) {
            dataDao.createData(dataModel)
        }
    }

    fun updateData(dataModel: DataModel) = runBlocking {
        this.launch(Dispatchers.IO) {
            dataDao.updateData(dataModel)
        }
    }


    fun deleteAnak(dataModel: DataModel) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                dataDao.deleteData(dataModel)
            }
        }
    }

    fun getData(): LiveData<List<DataModel>> {
        return listData
    }
}