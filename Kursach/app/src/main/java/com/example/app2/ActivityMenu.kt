package com.example.app2

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_menu.*

class ActivityMenu: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_menu)

        myRecycler.setHasFixedSize(true)
        myRecycler.layoutManager = LinearLayoutManager(this)

        myRecycler.adapter = AdapterRecycler(this)
    }
}