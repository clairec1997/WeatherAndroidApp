package com.mobile.clairecannatti.theweatherapp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.clairecannatti.theweatherapp.MainActivity
import com.mobile.clairecannatti.theweatherapp.R
import com.mobile.clairecannatti.theweatherapp.data.AppDatabase
import com.mobile.clairecannatti.theweatherapp.data.WeatherReport
import com.mobile.clairecannatti.theweatherapp.touch.WRTouchHelperAdapter
import kotlinx.android.synthetic.main.weather_dialog.view.*
import kotlinx.android.synthetic.main.weather_row.view.*
import java.util.*

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.ViewHolder>,
        WRTouchHelperAdapter {

    val context : Context
    private val reportsList = mutableListOf<WeatherReport>()


    constructor(context: Context, shoppingList: List<WeatherReport>) : super() {
        this.context = context
        this.reportsList.addAll(shoppingList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
                R.layout.weather_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reportsList.size
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherReport = reportsList[position]

        holder.tvCity.text = weatherReport.cityName

        holder.btnDelete.setOnClickListener{
            deleteReport(holder.adapterPosition)
        }

        holder.weatherCard.setOnClickListener {
            (context as MainActivity).showDetailsActivity(
                    weatherReport, holder.adapterPosition
            )
        }
    }

    private fun deleteReport(adapterPosition: Int) {
        Thread {
            AppDatabase.getInstance(context).wrDao().deleteItem(
                    reportsList[adapterPosition]
            )

            reportsList.removeAt(adapterPosition)

            (context as MainActivity).runOnUiThread {
                notifyItemRemoved(adapterPosition)
            }
        }.start()


    }

    fun addItem(weatherReport: WeatherReport) {
        reportsList.add(itemCount, weatherReport)
        notifyItemInserted(itemCount)
    }

//    fun deleteAll() {
//        for (i in 0..(itemCount-1)) {
//            deleteReport(0)}
//    }


    inner class ViewHolder(wrView: View) : RecyclerView.ViewHolder(wrView){
        val tvCity = wrView.tvCity
        val btnDelete = wrView.btnDelete
        val weatherCard = wrView.weatherCard
    }

    override fun onDismissed(position: Int) {
        deleteReport(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(reportsList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    fun updateItem(weatherReport: WeatherReport, idx: Int) {
        reportsList[idx] = weatherReport
        notifyItemChanged(idx)
    }


}