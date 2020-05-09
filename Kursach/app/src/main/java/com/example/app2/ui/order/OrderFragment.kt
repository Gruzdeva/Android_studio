package com.example.app2.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.R


class OrderFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_orders, container, false)
        val myRecylcer = root.findViewById<RecyclerView>(R.id.myOrdersRecycler)

        val tableOrders = TableOrders(activity!!.applicationContext)
        tableOrders.load_in_singleton()
        val size = tableOrders.itemCount()

        myRecylcer.layoutManager = LinearLayoutManager(activity)
        myRecylcer.setHasFixedSize(true)

        myRecylcer.adapter = AdapterOrderRecycler(activity!!.applicationContext, size)
        return root
    }
}