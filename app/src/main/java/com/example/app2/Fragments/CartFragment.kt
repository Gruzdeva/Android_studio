package com.example.app2.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.R
import com.example.app2.Adapters.AdapterCartRecycler
import com.example.app2.Singletons.UserProfile
import com.example.app2.Tables.TableUserOrder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CartFragment: Fragment() {
    private lateinit var auth: FirebaseAuth
    val userProfile = UserProfile.getInstance()

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

        val cost = tableUserOrder.order_cost()

        tableUserOrder.updateTableData()
        cartRecycler.layoutManager = LinearLayoutManager(activity)
        cartRecycler.setHasFixedSize(true)

        cartRecycler.adapter =
            AdapterCartRecycler(
                activity!!.applicationContext,
                root
            )
        auth = Firebase.auth
        val userId = auth.currentUser!!.uid

        val db = Firebase.database
        val dbUsers = db.getReference("Users")

        dbUsers.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val points =
                    dataSnapshot.child(userId).child("points").getValue(Int::class.java)
                        ?.toInt()!!

                if (userProfile.isPointsDeduct){
                    if (cost < points) {
                        costView.text = "0"
                    } else {
                        costView.text = "COST: ${cost - points}"
                    }
                } else {
                    costView.text = "COST: ${cost}"
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Toast.makeText(activity!!.applicationContext, "Ошибка загрузки данных",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })

        return root
    }

}