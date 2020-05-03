package com.example.app2

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager


class ActivityPagerMenu: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager_menu)

        val intent = intent
        val menuFragAdapter = MenuFragAdapter(supportFragmentManager, this, 42)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)

        viewPager.adapter = menuFragAdapter
        viewPager.currentItem = intent.getIntExtra("position", 0)
    }
}