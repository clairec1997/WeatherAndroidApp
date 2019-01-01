package com.mobile.clairecannatti.theweatherapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.mobile.clairecannatti.theweatherapp.data.WeatherResult
import com.mobile.clairecannatti.theweatherapp.network.WeatherAPI
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.weather_dialog.*
import kotlinx.android.synthetic.main.weather_row.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {

    private val HOST_URL = "https://api.openweathermap.org/"

    private var city = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        if (intent.hasExtra("It works")) {
            city = intent.getStringExtra("It works")
        }

        val retrofit = Retrofit.Builder()
                .baseUrl(HOST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val weatherAPI = retrofit.create(WeatherAPI::class.java)
        val weatherCall = weatherAPI.getWeather(city, "Imperial", "1389a54f6686f9b65515804e598284c0")
        weatherCall.enqueue(object : Callback<WeatherResult> {

            override fun onFailure(call: Call<WeatherResult>?, t: Throwable) {
                tvCity.text = t.message
            }

            override fun onResponse(call: Call<WeatherResult>?, response: Response<WeatherResult>) {
                val weatherResult = response.body()
                tvDesc.text = "${weatherResult?.weather?.get(0)?.description}"
                tvCityName.text = city
                tvMin.text = "${weatherResult?.main?.temp_min}${getString(R.string.degF)}"
                tvMax.text = "${weatherResult?.main?.temp_max}${getString(R.string.degF)}"
                tvCurrentTemp.text = "${weatherResult?.main?.temp}${getString(R.string.degF)}"
                tvCloudCover.text = "${weatherResult?.clouds?.all}${getString(R.string.cloudCover)}"
                tvHumidity.text = "${weatherResult?.main?.humidity}${getString(R.string.humidity)}"

                Glide.with(this@DetailsActivity)
                        .load(
                                ("https://openweathermap.org/img/w/" +
                                        response.body()?.weather?.get(0)?.icon
                                        + ".png")) .into(ivIcon)
            }
        })
    }
}
