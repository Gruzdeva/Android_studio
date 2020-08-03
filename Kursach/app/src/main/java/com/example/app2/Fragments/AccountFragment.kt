package com.example.app2.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.app2.DataClasses.User
import com.example.app2.R
import com.example.app2.Singletons.UserProfile
import com.example.app2.Tables.TableUsers
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class AccountFragment: Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_account, container, false)

//        val tableUsers =
//            TableUsers(activity!!.applicationContext)
//        var userProfile = tableUsers.loadRemember()
        val db = Firebase.database
        val dbUsers = db.getReference("Users")

        val nameView: TextView = root.findViewById(R.id.name_of_account)
        val pointsView: TextView = root.findViewById(R.id.points_acc)

        auth = Firebase.auth
        // Read from the database
        dbUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val userId = auth.currentUser!!.uid

                val name = dataSnapshot.child(userId).child("name").getValue(String::class.java).toString()
                val points = dataSnapshot.child(userId).child("points").getValue(Long::class.java).toString()
                nameView.text = name
                pointsView.text = "Points: $points"
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Toast.makeText(activity!!.applicationContext, "Ошибка загрузки данных" ,
                    Toast.LENGTH_SHORT).show()
            }
        })
        return root
    }
}