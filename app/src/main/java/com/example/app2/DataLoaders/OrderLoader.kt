package com.example.app2.DataLoaders

import android.os.AsyncTask
import android.util.Log
import com.example.app2.DataClasses.Orders
import com.example.app2.Singletons.OrderSingleton
import com.google.gson.Gson

class OrderLoader(info: String) : AsyncTask<String, Void, String>() {
    val info = info
    override fun doInBackground(vararg params: String?): String {
        val orderSingleton = OrderSingleton.getInstance()!!
        var gson = Gson()

        val data = gson.fromJson(info, Array<Orders>::class.java)
        Log.d("SINGLETONS", info)

        var i = 0
        for (item in data) {
            orderSingleton.dates[i] = item.orderDate
            orderSingleton.costs[i] = item.orderPrice

        }

        return "0"
    }

}