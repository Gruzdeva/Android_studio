package com.example.app2.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.R


class CartFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_cart, container, false)
        val cartRecycler = root.findViewById<RecyclerView>(R.id.myCartRecycler)
        val cost = root.findViewById<TextView>(R.id.cart_cost)
        val tableUserOrder =
            TableUserOrder(activity!!.applicationContext)
        tableUserOrder.updateTableData()
        cartRecycler.layoutManager = LinearLayoutManager(activity)
        cartRecycler.setHasFixedSize(true)

        cartRecycler.adapter =
            AdapterCartRecycler(activity!!.applicationContext)

        cost.text = "COST: ${tableUserOrder.order_cost()}"
        return root
    }


}