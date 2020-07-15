package com.example.app2.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.R
import com.example.app2.Adapters.AdapterCartRecycler
import com.example.app2.Singletons.UserProfile
import com.example.app2.Tables.TableUserOrder

class CartFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_cart, container, false)
        val cartRecycler = root.findViewById<RecyclerView>(R.id.myCartRecycler)
        val costView = root.findViewById<TextView>(R.id.cart_cost)
        val tableUserOrder =
            TableUserOrder(activity!!.applicationContext)
        val userProfile = UserProfile.getInstance()
        val cost = tableUserOrder.order_cost()

        tableUserOrder.updateTableData()
        cartRecycler.layoutManager = LinearLayoutManager(activity)
        cartRecycler.setHasFixedSize(true)

        cartRecycler.adapter =
            AdapterCartRecycler(
                activity!!.applicationContext,
                root
            )

        if (userProfile.isPointsDeduct){
            if (cost < userProfile.points) {
                costView.text = "0"
            } else {
                costView.text = "COST: ${cost - userProfile.points}"
            }
        } else {
            costView.text = "COST: ${cost}"
        }
        return root
    }
}