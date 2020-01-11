package com.lazday.crudroomimagekotlin.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [DataModel::class],
    version = 1,
    exportSchema = false
)

abstract class MyDatabase : RoomDatabase() {

    abstract fun dataDao(): DataDao

    companion object {
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase? {
            if (INSTANCE == null) {
                synchronized(MyDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context,
                        MyDatabase::class.java,
                        "myDb_12345")
                        .build()
                }
            }
            return INSTANCE
        }
    }
}