package com.example.app2.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.app2.DataClasses.Orders
import com.example.app2.R
import com.example.app2.Tables.TableUserOrder
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class Menu : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var auth: FirebaseAuth
    val format = SimpleDateFormat("dd.MM.yyyy kk.mm")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        FirebaseApp.initializeApp(this)


//        val tableMenu = TableMenu(this)
//        tableMenu.loadFromDB()
        val tableUserOrder = TableUserOrder(this)
        tableUserOrder.delete_db_data()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_menu,
                R.id.nav_orders,
                R.id.nav_cart,
                R.id.nav_account
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun log_out(view: View) { // work in progress
//        val tableUsers = TableUsers(this)
//
//        tableUsers.logOut()
        Firebase.auth.signOut()
        startActivity(Intent(this, ActivityAuthorization::class.java))// finish add
    }

    fun pay_for_order(view: View) {
        val tableUserOrder = TableUserOrder(this)

        auth = Firebase.auth
        val userId = auth.currentUser!!.uid

        val db = Firebase.database
        val dbUsers = db.getReference("Users")
        val dbOrders = db.getReference("Orders")

        val cost = tableUserOrder.order_cost()
        var uPoints = 0
        var points = 0
        if(cost != 0) {

            dbUsers.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    uPoints = dataSnapshot.child(userId).child("points").getValue(Int::class.java)?.toInt()!!
                    points = uPoints?.plus(cost * 0.05).toInt()
                    dbUsers.child(userId).child("points").setValue(points)
                    Log.d("POINTS", uPoints.toString())
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Toast.makeText(this@Menu, "Ошибка загрузки данных" ,
                        Toast.LENGTH_SHORT).show()
                }

            })

            val toast = Toast.makeText(this, "Заказ принят", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()

            tableUserOrder.delete_db_data()

            val key = dbOrders.push().key
            val date = format.format(Date())
            val order = Orders(userId, date, cost)

            val orderValues = order.toMap()

            val childUpdates = hashMapOf<String, Any>(
                "/all-orders/$key" to orderValues,
                "/user-orders/$userId/$key" to orderValues
            )

            dbOrders.updateChildren(childUpdates)
        } else {
            val toast = Toast.makeText(this, "Корзина пуста", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()
        }

    }

    fun deduct_points(view: View) {
        val toast = Toast.makeText(this, "Функция в разработке", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()

//        val tableUserOrder = TableUserOrder(this)
//        val costView: TextView = this.findViewById(R.id.cart_cost)
//        val userProfile = UserProfile.getInstance()
//        val cost = tableUserOrder.order_cost()
//
//        userProfile.isPointsDeduct = true
//
//        if (cost < userProfile.points) costView.text = "0"
//        else costView.text = "COST: ${cost - userProfile.points}"
    }
}
