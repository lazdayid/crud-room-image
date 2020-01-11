package com.lazday.crudroomimagekotlin.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DataDao {

    @Insert
    suspend fun createData(dataModel: DataModel)

    @Update
    suspend fun updateData(dataModel: DataModel)

    @Delete
    suspend fun deleteData(dataModel: DataModel)

//    @Query("SELECT * FROM myTable ORDER BY id DESC")
//    fun getData(): List<DataModel>

    @Query("SELECT * FROM myTable ORDER BY id DESC")
    fun getData(): LiveData<List<DataModel>>
}