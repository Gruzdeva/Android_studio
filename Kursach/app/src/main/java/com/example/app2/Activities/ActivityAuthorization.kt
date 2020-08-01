package com.example.app2.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.app2.*
import com.example.app2.Singletons.UserProfile
import com.example.app2.Tables.TableUsers
import com.example.app2.Tables.TableOrders
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ActivityAuthorization : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
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
        var userProfile = UserProfile.getNewObject()

        val loginText: EditText = findViewById(R.id.login_auth)
        val passwordText: EditText = findViewById(R.id.password_auth)

        val login = loginText.text.toString()
        val password = passwordText.text.toString()

        auth = Firebase.auth
        auth.signInWithEmailAndPassword(login, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    userProfile.login = user?.displayName.toString()
                    startActivity(Intent(this, Menu::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Ошибка авторизации.",
                        Toast.LENGTH_SHORT).show()
                    // ...
                }

                // ...
            }
//        if (!tableUsers.checkProfile(login, password)){
//            val toast =  Toast.makeText(this, "Логин или пароль неправильные, проверьте данные", Toast.LENGTH_SHORT)
//            toast.setGravity(Gravity.TOP, 0, 0)
//            toast.show()
//        } else {
//            userProfile.login = login
//            userProfile.password = password
//
//            tableUsers.signIn(login, password)
//            tableUsers.close()
//
//            startActivity(Intent(this, Menu::class.java))
//        }

    }

    fun delete(view: View) {
        val tableUsers = TableUsers(this)
        val tableOrders = TableOrders(this)
        tableUsers.deleteFromTable()
        tableOrders.deleteFromDB()

        val toast = Toast.makeText(this, "DELETE", Toast.LENGTH_SHORT)
        toast.show()
    }
}

