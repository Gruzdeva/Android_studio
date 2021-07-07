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
import com.example.app2.Adapters.AdapterRecycler
import com.example.app2.DataClasses.MenuData
import com.example.app2.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PagerFragment: Fragment() {// Пока что немного багует
    val CATEGORY = "Category"

    val db = Firebase.database
    private lateinit var dbMenu: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        val arguments = arguments
        val myRecycler = view.findViewById<RecyclerView>(R.id.myRecycler)
        var category = 0

        arguments?.let {        // берется категория блюд, чтобы отображать только ее в определнном списке
            category = arguments.getInt(CATEGORY)
            Log.d("CATEGORYF", category.toString())
            dbMenu = db.getReference("Menu/$category")
        }


        dbMenu.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                val menuArray: ArrayList<MenuData> = ArrayList()
                for(item in dataSnapshot.children) {

                    val name = item.child("name").value as String?
                    val description = item.child("description").value as String?
                    val price = item.child("price").getValue(Int::class.java)

                    menuArray.add(MenuData(name, description, price))
                }

                val size = dataSnapshot.childrenCount.toInt()

                updateUI(myRecycler, size, menuArray)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Toast.makeText(activity!!.applicationContext, "Ошибка загрузки данных",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })

        return view
    }

    private fun updateUI(myRecycler: RecyclerView, size: Int, menuArray: ArrayList<MenuData>) {
        myRecycler.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        myRecycler.setHasFixedSize(true)

        myRecycler.adapter = AdapterRecycler(
            size,
            menuArray
        )
    }

}