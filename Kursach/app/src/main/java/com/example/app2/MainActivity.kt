package com.example.app2

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.EditText
import com.example.app2.FeedReaderContract.UserTable

class MainActivity : AppCompatActivity() {
    val dbHelper = DBHelperUsers(this)
    val db: SQLiteDatabase
        get() = dbHelper.readableDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun registration(view: View) {
        startActivity(Intent(this, RegistrationActivity::class.java))
    }

    fun forgot_password(view: View) {
        startActivity(Intent(this, ForgotPasswordActivity::class.java))
    }

    fun go_to_app(view: View) {
        startActivity(Intent(this, Menu::class.java))
    }
}

