package com.example.app2.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.app2.Adapters.AdapterPagerFragment
import com.example.app2.R
import com.example.app2.Adapters.AdapterRecycler

class MenuFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_menu_pager, container, false)

        val myAdapter = AdapterPagerFragment(fragmentManager!!, activity!!.applicationContext, 5)
        val viewPager = root.findViewById<ViewPager>(R.id.viewpager)

        viewPager.adapter = myAdapter
        return root
    }
}