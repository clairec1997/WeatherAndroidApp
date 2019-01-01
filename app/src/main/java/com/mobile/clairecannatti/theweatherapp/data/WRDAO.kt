package com.mobile.clairecannatti.theweatherapp.data


import android.arch.persistence.room.*

@Dao
interface WRDAO {

    @Query("SELECT * FROM items")
    fun findAllItems(): List<WeatherReport>

    @Insert
    fun insertItem(item: WeatherReport) : Long

    @Delete
    fun deleteItem(item: WeatherReport)

    @Update
    fun updateItem(item: WeatherReport)
}