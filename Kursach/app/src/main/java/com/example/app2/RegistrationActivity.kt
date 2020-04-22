package com.example.app2

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.app2.FeedReaderContract.FeedEntry

class RegistrationActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

    }

    fun createAccount(view: View) {
        val dbHelper = DataBaseHelper(this)
        val db = dbHelper.writableDatabase

        val loginText: EditText = findViewById(R.id.login_reg)
        val password_text: EditText = findViewById(R.id.password_reg)
        val password2_text: EditText = findViewById(R.id.password2_reg)

        val login = loginText.text.toString()
        val password = password_text.text.toString()
        val password2 = password2_text.text.toString()


        if(password.compareTo(password2) == 0){
            val values = ContentValues().apply {
                put(FeedEntry.COLUmN_NAME_LOGIN, login)
                put(FeedEntry.COLUmN_NAME_PASSWORD, password)
            }

            val newRowId = db?.insert(FeedEntry.TABLE_NAME, null, values)

            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}