package com.example.app2.ui.menu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.app2.R


class ActivityPagerMenu: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager_menu)

        val intent = intent
        val menuFragAdapter = MenuFragAdapter(
            supportFragmentManager,
            this,
            42
        )
        val viewPager = findViewById<ViewPager>(R.id.viewPager)

        viewPager.adapter = menuFragAdapter
        viewPager.currentItem = intent.getIntExtra("position", 0)
    }
}