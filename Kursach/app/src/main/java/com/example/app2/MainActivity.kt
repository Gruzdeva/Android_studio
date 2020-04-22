package com.example.app2

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.EditText
import com.example.app2.FeedReaderContract.FeedEntry

class MainActivity : AppCompatActivity() {
    val dbHelper = DataBaseHelper(this)
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
//        val loginText: EditText = findViewById(R.id.username)
//        val passwordText: EditText = findViewById(R.id.password)
//
//        val login = loginText.text.toString()
//        val password = passwordText.text.toString()
//
//
//
//        val cursor = db.rawQuery("SELECT * FROM ${FeedEntry.TABLE_NAME}", null)
//
//        val itemsIds = mutableListOf<Long>()
//        with(cursor){
//            while(moveToNext()){
//                val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
//                itemsIds.add(itemId)
//            }
//        }
//
//        if(!cursor.isAfterLast){
//            if(cursor.getString(1).compareTo(login) == 0 && cursor.getString(2).compareTo(password) == 0){
               startActivity(Intent(this, Menu::class.java))
//            }
//        }
    }
}

