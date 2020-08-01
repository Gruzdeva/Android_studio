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
import com.example.app2.R
import com.example.app2.Tables.TableUsers
import com.example.app2.Singletons.UserProfile
import com.example.app2.Tables.TableUserOrder
import com.example.app2.Tables.TableMenu
import com.example.app2.Tables.TableOrders
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase

class Menu : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var firebaseAnalytics: FirebaseAnalytics

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

    fun log_out(view: View) {
        val tableUsers = TableUsers(this)

        tableUsers.logOut()

        startActivity(Intent(this, ActivityAuthorization::class.java))// finish add
    }

    fun pay_for_order(view: View) {
        val tableUserOrder = TableUserOrder(this)
        val tableUsers = TableUsers(this)
        val tableOrders = TableOrders(this)
        var userProfile = UserProfile.getInstance()

        val cost = tableUserOrder.order_cost()

        if(cost != 0) {
            if (userProfile.isPointsDeduct){
                if (cost < userProfile.points){
                    userProfile.points = userProfile.points - cost
                    tableUsers.updatePoints(userProfile.points)
                    userProfile.isPointsDeduct = false
                } else {
                    userProfile.points = ((cost - userProfile.points) * 0.05).toInt()
                    tableUsers.updatePoints(userProfile.points)

                    userProfile.isPointsDeduct = false
                }
            } else {
                userProfile.points = (userProfile.points + cost * 0.05).toInt()
                tableUsers.updatePoints(userProfile.points)
            }

            val toast = Toast.makeText(this, "Заказ принят", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()

            tableUserOrder.delete_db_data()
            tableOrders.addNewOrder(cost)
        } else {
            val toast = Toast.makeText(this, "Корзина пуста", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()
        }
    }

    fun deduct_points(view: View) {
        val tableUserOrder = TableUserOrder(this)
        val costView: TextView = this.findViewById(R.id.cart_cost)
        val userProfile = UserProfile.getInstance()
        val cost = tableUserOrder.order_cost()

        userProfile.isPointsDeduct = true

        if (cost < userProfile.points) costView.text = "0"
        else costView.text = "COST: ${cost - userProfile.points}"
    }
}
