package com.example.app2.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.AdapterRecycler
import com.example.app2.R

class MenuFragment: Fragment() {

    private lateinit var menuViewModel: MenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        menuViewModel =
//            ViewModelProviders.of(this).get(MenuViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_menu, container, false)
//        val textView: TextView = root.findViewById(R.id.text_menu)
//        menuViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        val myRecycler = activity!!.findViewById<RecyclerView>(R.id.myRecycler)

//        myRecycler.layoutManager = LinearLayoutManager(activity)
//        myRecycler.setHasFixedSize(true)
//
//        myRecycler.adapter = AdapterRecycler(activity!!.applicationContext)
        return root
    }
}