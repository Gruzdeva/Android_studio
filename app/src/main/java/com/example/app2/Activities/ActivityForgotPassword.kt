package com.example.app2.Activities

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.app2.R

class ActivityForgotPassword: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
    }

    fun checkPass(view: View) {

        val loginText: EditText = findViewById(R.id.login_forgot)
        val nameText: EditText = findViewById(R.id.name_forgot)
        val yourPassView: TextView = findViewById(R.id.your_pass)
        val passwordView: TextView = findViewById(R.id.passwrd_forgot_pass)

        val login = loginText.text.toString()
        val name = nameText.text.toString()

//        if(!tableUsers.checkForgot(login, name)){
//            val toast = Toast.makeText(this, "Не удалось найти пользователя, проверьте информацию или зарегестрируйтесь", Toast.LENGTH_SHORT)
//            toast.setGravity(Gravity.TOP, 0,0)
//            toast.show()
//        } else {
//            val pass = tableUsers.getPass(login, name)
//            yourPassView.text = "Your password:"
//            passwordView.text = pass
//        }
    }
}