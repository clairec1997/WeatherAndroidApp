package com.mobile.clairecannatti.theweatherapp.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.mobile.clairecannatti.theweatherapp.R

@Database(entities = arrayOf(WeatherReport::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun wrDao(): WRDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, context.getString(R.string.dbName))
                        .build()
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}