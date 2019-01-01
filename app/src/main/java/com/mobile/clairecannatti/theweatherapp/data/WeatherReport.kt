package com.mobile.clairecannatti.theweatherapp.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "items")
data class WeatherReport (
        @PrimaryKey(autoGenerate = true) var itemId: Long?,
        @ColumnInfo(name = "cityName") var cityName: String
) : Serializable