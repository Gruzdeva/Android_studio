package com.example.app2.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.widget.TextView
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
import com.example.app2.Singletons.MenuSingleton
import com.example.app2.Singletons.UserProfile
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
    private val format = SimpleDateFormat("dd.MM.yyyy kk.mm")

    val userprofile = UserProfile.getInstance()

    val db = Firebase.database
    private val dbUsers = db.getReference("Users")
    private val dbOrders = db.getReference("Orders")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        FirebaseApp.initializeApp(this)

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

    fun log_out(view: View) {
        Firebase.auth.signOut()
        startActivity(Intent(this, ActivityAuthorization::class.java))
    }

    fun pay_for_order(view: View) {
        val tableUserOrder = TableUserOrder(this)

        auth = Firebase.auth
        val userId = auth.currentUser!!.uid

        val cost = tableUserOrder.order_cost()
        var uPoints = 0

        if(cost != 0) {
            dbUsers.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    uPoints =
                        dataSnapshot.child(userId).child("points").getValue(Int::class.java)
                            ?.toInt()!!
                    updateUI(uPoints, cost, userId)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Toast.makeText(this@Menu, "Ошибка загрузки данных",
                        Toast.LENGTH_SHORT
                    ).show()
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

    private fun updateUI(uPoints: Int, cost: Int, userId: String) {
        val dbPoint = dbUsers.child(userId).child("points")
        var points = 0
        //Проверки для вычисления количества баллов
        if (userprofile.isPointsDeduct){
            if (cost < uPoints) {
                points = uPoints - cost
                dbPoint.setValue(points)
                Log.d("UPOINTS<", points.toString())
                userprofile.isPointsDeduct = false
            } else {
                points = ((cost - uPoints) * 0.05).toInt()
                dbPoint.setValue(points)
                Log.d("UPOINTS>", points.toString())
                userprofile.isPointsDeduct = false
            }

        } else {
            points = uPoints.plus(cost * 0.05).toInt()
            dbPoint.setValue(points)
        }
    }

    fun deduct_points(view: View) {
        val tableUserOrder = TableUserOrder(this)
        val cost = tableUserOrder.order_cost()
        val costView: TextView = this.findViewById(R.id.cart_cost)

        userprofile.isPointsDeduct = true
        val userId = auth.currentUser!!.uid

        dbUsers.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                val points =
                    dataSnapshot.child(userId).child("points").getValue(Int::class.java)
                        ?.toInt()!!
                updateUI_Cost(points, cost, costView)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Toast.makeText(this@Menu, "Ошибка загрузки данных",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun updateUI_Cost(points: Int, cost: Int, costView: TextView) {
        if (cost < points) costView.text = "0"
        else costView.text = "COST: ${cost - points}"
    }
}
