package com.example.app2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ActivitySplashScreen: AppCompatActivity() {
    private var flag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()

        val tableUsers = TableUsers(this)
        if(!flag){
            Thread(Runnable {
                Thread.sleep(2000)

                if(tableUsers.checkRemember()) {
                    tableUsers.loadRemember()
                    startActivity(Intent(this, Menu::class.java))
                } else
                    startActivity(Intent(this, ActivityAuthorization::class.java))
                finish()
            }).start()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("flag", true)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        flag = savedInstanceState.getBoolean("flag")
    }
}