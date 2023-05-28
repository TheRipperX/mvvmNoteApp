package com.example.mvvmnoteapp.utils

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner


fun Spinner.setItemsSpinner(list: MutableList<String>, itemBack: (String) -> Unit) {
    val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, list)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    this.adapter = adapter
    this.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            itemBack(list[p2])
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {

        }
    }
}



data class DataStatus<out T>(val success: Status, val data:T? = null, val empty: Boolean) {
    enum class Status{
        SUCCESS
    }

    companion object {
        fun<T> success(data: T?, empty: Boolean): DataStatus<T> {
            return DataStatus(Status.SUCCESS, data, empty)
        }
    }
}