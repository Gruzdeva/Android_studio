package com.example.app2

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.app2.DBReader.MenuSingleton

class MenuFragAdapter(fm: FragmentManager, context: Context, size: Int)
    : FragmentPagerAdapter(fm){
    val size = size
    private val menuInfoSingleton: MenuInfoSingleton = MenuInfoSingleton.getInstance()

    override fun getItem(position: Int): Fragment {
        val arguments = Bundle()

        arguments.putString(MenuSingleton.FOOD_NAME, menuInfoSingleton.name[position + 1])
        arguments.putString(MenuSingleton.FOOD_DESCRIPTION, menuInfoSingleton.description[position + 1])
        arguments.putString(MenuSingleton.FOOD_PRICE, menuInfoSingleton.price[position + 1].toString())

        val  menuFrag = MenuInfoFragment()
        menuFrag.arguments = arguments

        return menuFrag
    }

    override fun getCount(): Int {
        return size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return menuInfoSingleton.name[position + 1]
    }
}