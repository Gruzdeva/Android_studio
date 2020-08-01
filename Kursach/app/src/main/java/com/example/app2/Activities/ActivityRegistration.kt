package com.example.app2.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app2.R
import com.example.app2.Tables.TableUsers
import com.example.app2.Singletons.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ActivityRegistration: AppCompatActivity(){
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

    }

    fun createAccount(view: View) {
        val tableUsers = TableUsers(this)
        val userProfile = UserProfile.getInstance()

        val loginText: EditText = findViewById(R.id.login_reg)
        val passwordText: EditText = findViewById(R.id.password_reg)
        val password2Text: EditText = findViewById(R.id.password2_reg)
        val nameText: EditText = findViewById(R.id.name_reg)

        val login = loginText.text.toString()
        val password = passwordText.text.toString()
        val password2 = password2Text.text.toString()
        val name = nameText.text.toString()

        // Initialize Firebase database
        val db = Firebase.database
        val dbUsers = db.getReference("Users")

        // Initialize Firebase Auth
        auth = Firebase.auth

        //checker
        if(login == ""){
            val toast =
                Toast.makeText(this, "Логин не может быть пустым", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()
        } else if (password.length < 5){
            val toast =
                Toast.makeText(this, "Пароль должен содержать не менее 5 символов", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()
        }else if(password.compareTo(password2) != 0){
            val toast = Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()
        } else {
            userProfile.login = loginText.text.toString()
            userProfile.name = nameText.text.toString()
            userProfile.password = passwordText.text.toString()
            userProfile.points = 0

            auth.createUserWithEmailAndPassword(loginText.text.toString(), passwordText.text.toString())
                .addOnCompleteListener(this) {task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        dbUsers.setValue(userProfile)
                        startActivity(Intent(this, Menu::class.java))
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Ошибка регистрации." ,
                            Toast.LENGTH_SHORT).show()
                    }
                }
//            tableUsers.signUp()
//            tableUsers.close()
        }
    }

}