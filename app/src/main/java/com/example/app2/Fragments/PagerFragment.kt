package com.example.app2.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.Adapters.AdapterPagerFragment
import com.example.app2.Adapters.AdapterRecycler
import com.example.app2.R
import com.example.app2.Tables.TableMenu

class PagerFragment: Fragment() {
    val CATEGORY = "Category"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        val tableMenu = TableMenu(activity!!.applicationContext)
        val arguments = arguments
        val myRecycler = view.findViewById<RecyclerView>(R.id.myRecycler)
        var category = 0

        arguments?.let {
            category = arguments.getInt(CATEGORY)
            Log.d("CATEGORYF", category.toString())
        }

        tableMenu.loadFromDB(category)
        myRecycler.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        myRecycler.setHasFixedSize(true)

        myRecycler.adapter = AdapterRecycler(
                activity!!.applicationContext,
                tableMenu.getItemCount(category))

        return view
    }
}