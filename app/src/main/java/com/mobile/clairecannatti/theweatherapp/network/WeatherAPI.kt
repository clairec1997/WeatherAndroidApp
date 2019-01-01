package com.mobile.clairecannatti.theweatherapp.network

import com.mobile.clairecannatti.theweatherapp.data.WeatherResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// HOST: https://api.exchangeratesapi.io
//
// PATH: /latest
//
// QUERY param separator: ?
// QUERY params: base

interface WeatherAPI {
    @GET("/data/2.5/weather")
    fun getWeather(@Query("q") city: String,
                   @Query("units") units: String,
                   @Query("appid") appid: String)
            : Call<WeatherResult>
}