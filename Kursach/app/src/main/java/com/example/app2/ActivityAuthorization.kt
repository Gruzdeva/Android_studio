package com.example.app2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast

class ActivityAuthorization : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun registration(view: View) {
        startActivity(Intent(this, ActivityRegistration::class.java))
    }

    fun forgot_password(view: View) {
        startActivity(Intent(this, ActivityForgotPassword::class.java))
    }

    fun login_now(view: View) {
        val tableUsers = TableUsers(this)
        val userProfile = UserProfile.getNewObject()

        val loginText: EditText = findViewById(R.id.login_auth)
        val passwordText: EditText = findViewById(R.id.password_auth)

        val login = loginText.text.toString()
        val password = passwordText.text.toString()

        if (!tableUsers.checkProfile(login, password)){
            val toast =  Toast.makeText(this, "Логин или пароль неправильные, проверьте данные", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()
        } else {
            userProfile.login = login
            userProfile.password = password

            tableUsers.signIn()
            tableUsers.close()

            startActivity(Intent(this, Menu::class.java))
        }

    }

    fun delete(view: View) {
        val tableUsers = TableUsers(this)
        tableUsers.deleteFromTable()

        val toast = Toast.makeText(this, "DELETE", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun proverka(view: View) {
        val tableUsers = TableUsers(this)
        tableUsers.tableInfo()
        tableUsers.close()

        val toast = Toast.makeText(this, "CHECK", Toast.LENGTH_SHORT)
        toast.show()
    }
}

