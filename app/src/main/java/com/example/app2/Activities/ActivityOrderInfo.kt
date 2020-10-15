package com.example.app2.Activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app2.Adapters.AdapterOrderInfo
import com.example.app2.DataClasses.OrderUser
import com.example.app2.DataClasses.Orders
import com.example.app2.R
import com.example.app2.Singletons.OrderSingleton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_order_info.*

class ActivityOrderInfo: AppCompatActivity() {

    val db = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_info)

        val key = intent.getStringExtra("key")

        val dbOrderInfo = db.getReference("Orders/order-info/$key")


        dbOrderInfo.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val orderInfoArray: ArrayList<OrderUser> = ArrayList()

                for ( item in dataSnapshot.children) {
                    val name = item.child("name").value as String?
                    val price = item.child("price").getValue(Int::class.java)

                    orderInfoArray.add(OrderUser(name, price))
                }

                val size = dataSnapshot.childrenCount.toInt()
                updateUI(size, orderInfoArray)

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Toast.makeText(this@ActivityOrderInfo, "Ошибка загрузки данных" ,
                    Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun updateUI(size: Int, orderInfoArray: ArrayList<OrderUser>) {
        rv_order_info.layoutManager = LinearLayoutManager(this)
        rv_order_info.setHasFixedSize(true)
        rv_order_info.adapter = AdapterOrderInfo(size, orderInfoArray)
    }
}