package com.example.app2.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.R
import com.example.app2.Adapters.AdapterOrderRecycler
import com.example.app2.Singletons.OrderSingleton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class OrderFragment: Fragment() {
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_orders, container, false)
        val myRecycler = root.findViewById<RecyclerView>(R.id.myOrdersRecycler)

        auth = Firebase.auth
        val userId = auth.currentUser!!.uid

        val db = Firebase.database
        val dbOrders = db.getReference("Orders/user-orders/$userId")

        dbOrders.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                var i = 0
                val orderSingleton = OrderSingleton.getInstance()!!
                for (item in dataSnapshot.children) {
                    orderSingleton.dates[i] = item.child("orderDate").value as String?
                    orderSingleton.costs[i] = item.child("orderPrice").getValue(Int::class.java)
                    i++
                }

                val size = dataSnapshot.childrenCount.toInt()
                val orderInfo = dataSnapshot.value
                updateUI(orderInfo.toString(), size, myRecycler)

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Toast.makeText(activity!!.applicationContext, "Ошибка загрузки данных" ,
                    Toast.LENGTH_SHORT).show()
            }
        })
        return root
    }

    private fun updateUI(info: String, size: Int, myRecycler: RecyclerView) {

        myRecycler.layoutManager = LinearLayoutManager(activity)
        myRecycler.setHasFixedSize(true)
        Log.d("SINGLETONS", info)

        myRecycler.adapter = AdapterOrderRecycler(
            activity!!.applicationContext, size
        )
    }
}