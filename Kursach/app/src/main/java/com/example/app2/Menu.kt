package com.example.app2

import android.content.Intent
import android.os.Bundle
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
import com.example.app2.ui.cart.TableUserOrder
import com.example.app2.ui.menu.TableMenu
import com.example.app2.ui.order.TableOrders

class Menu : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val tableMenu = TableMenu(this)
        tableMenu.loadFromDB()
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
                userProfile.points = (cost * 0.05).toInt()
                tableUsers.updatePoints(userProfile.points)
                userProfile.isPointsDeduct = false
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
        val cost: TextView = this.findViewById(R.id.cart_cost)
        val userProfile = UserProfile.getInstance()

        userProfile.isPointsDeduct = true
        cost.text = "COST: ${tableUserOrder.order_cost()}"
    }
}
