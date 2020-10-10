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
import com.example.app2.R
import com.example.app2.Singletons.MenuSingleton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PagerFragment: Fragment() {// Пока что немного багует
    val CATEGORY = "Category"

    val db = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        val arguments = arguments
        val myRecycler = view.findViewById<RecyclerView>(R.id.myRecycler)
        var category = 0

        arguments?.let {        // берется категория блюд, чтобы отображать только ее в определнном спсике
            category = arguments.getInt(CATEGORY)
            Log.d("CATEGORYF", category.toString())
        }

        val dbMenu = db.getReference("Menu/$category")

        dbMenu.addListenerForSingleValueEvent(object : ValueEventListener {     //Ааааааааааааа, бааааг(блюда находятся не в своих категориях)
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                val menuSingleton = MenuSingleton.getInstance()!!

                for((i, item) in dataSnapshot.children.withIndex()) {
                    menuSingleton.name[i] = item.child("name").value as String?
                    menuSingleton.description[i] = item.child("description").value as String?
                    menuSingleton.price[i] = item.child("price").getValue(Int::class.java)
                }

                val size = dataSnapshot.childrenCount.toInt()
                updateUI(myRecycler, size, menuSingleton)
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

    private fun updateUI(myRecycler: RecyclerView, size: Int, singleton: MenuSingleton) {
        myRecycler.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        myRecycler.setHasFixedSize(true)

        myRecycler.adapter = AdapterRecycler(
            activity!!.applicationContext,
            size
        )
    }
}