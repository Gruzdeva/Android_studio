package com.example.app2.Adapters

import androidx.fragment.app.FragmentManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.example.app2.Fragments.PagerFragment
import java.util.*

class AdapterPagerFragment(fm: FragmentManager, context: Context, size: Int)
: FragmentPagerAdapter(fm){

    val CATEGORY = "Category"
    val size = size
    val titles = arrayOf("Первые блюда", "Вторые блюда", "Салаты", "Десерты", "Напитки")

    override fun getItem(position: Int): Fragment {
        val arguments = Bundle()

        arguments.putInt(CATEGORY, position + 1)
        //позиция не всегда передается, или не всегда берется из аргументов, аааааааааааааааааааааааааааа
        val pagerFrag = PagerFragment()
        pagerFrag.arguments = arguments

        return pagerFrag
    }

    override fun getCount(): Int {
        return size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}