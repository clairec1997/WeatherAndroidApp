package com.mobile.clairecannatti.theweatherapp

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.*
import com.mobile.clairecannatti.theweatherapp.data.WeatherReport
//import kotlinx.android.synthetic.main.weather_dialog.*
import kotlinx.android.synthetic.main.weather_dialog.view.*
import kotlinx.android.synthetic.main.weather_row.view.*

class WeatherDialog : DialogFragment() {

    interface WeatherHandler {
        fun itemCreated(weatherReport: WeatherReport)
        fun itemUpdated(weatherReport: WeatherReport)
    }

    private lateinit var weatherHandler: WeatherHandler

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is WeatherHandler) {
            weatherHandler = context
        } else {
            throw RuntimeException("The activity does not implement the ItemHandler interface")
        }
    }

    private lateinit var actvCity: AutoCompleteTextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.addCity))

        val rootView = requireActivity().layoutInflater.inflate(R.layout.weather_dialog, null)

        actvCity = rootView.actvCity

        builder.setView(rootView)

        val arguments = this.arguments

//        if (arguments != null && arguments.containsKey(MainActivity.KEY_ITEM_TO_EDIT)) {
//            val weatherReport = arguments.getSerializable(
//                    MainActivity.KEY_ITEM_TO_EDIT
//            ) as WeatherReport
//        }

        builder.setPositiveButton(getString(R.string.add)){
            dialog, witch -> //empty
        }

        builder.setNegativeButton(getString(R.string.nvm)){
            dialog, witch ->
        }

        return builder.create()
    }

    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {

            if (actvCity.text.isNotEmpty()) {
//                val arguments = this.arguments
                handleItemCreate()

                dialog.dismiss()
            } else {
                actvCity.error = getString(R.string.emptyError)
            }
        }
    }

    private fun handleItemCreate() {
        weatherHandler.itemCreated(
                WeatherReport(
                        null,
                        actvCity.text.toString()
                )
        )
    }
}
