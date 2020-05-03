package com.example.app2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.app2.DBReader.MenuSingleton

class MenuInfoFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu_info, container, false)

        val arguments = arguments
        if(arguments != null){
            val food_name = arguments.getString(MenuSingleton.FOOD_NAME).toString()
            val food_description = arguments.getString(MenuSingleton.FOOD_DESCRIPTION).toString()
            val food_price = arguments.getString(MenuSingleton.FOOD_PRICE).toString()

            displayValues(view, food_name, food_description, food_price)
        }
        return view
    }

    private fun displayValues(v: View, name: String, description: String, price: String){
        val nameView: TextView = v.findViewById(R.id.name_pager)
        val descriptionView: TextView = v.findViewById(R.id.description_pager)
        val priceView: TextView = v.findViewById(R.id.price_pager)

        nameView.text = name
        descriptionView.text = description
        priceView.text = price
    }
}