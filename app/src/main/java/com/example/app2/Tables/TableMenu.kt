package com.example.app2.Tables

import android.content.Context
import android.util.Log
import com.example.app2.DBHelpers.DBReader.MenuTable
import com.example.app2.DBHelpers.DBHelperMenu
import com.example.app2.Singletons.MenuSingleton

class TableMenu(context: Context) {
    val dbHelper = DBHelperMenu(context)
    val db = dbHelper.writableDatabase

    var cursor = db.query(MenuTable.TABLE_NAME, null, null, null, null, null, null)

    val indexId = cursor.getColumnIndex(MenuTable.COLUMN_ID)
    val indexGroupId = cursor.getColumnIndex(MenuTable.COLUMN_GROUP_ID)
    val indexName = cursor.getColumnIndex(MenuTable.COLUMN_NAME)
    val indexDescription = cursor.getColumnIndex(MenuTable.COLUMN_DESCRIPTION)
    val indexPrice = cursor.getColumnIndex(MenuTable.COLUMN_PRICE)

    var menuSingleton = MenuSingleton.getNewObject()

    fun loadFromDB(category: Int) {
        cursor = db.rawQuery("SELECT * FROM ${MenuTable.TABLE_NAME} " +
                "WHERE ${MenuTable.COLUMN_GROUP_ID} = ?", arrayOf(category.toString()))
        cursor.moveToFirst()
        var i = 0

        Log.d("KOLVOl", "${category}")

        while (!cursor.isAfterLast) {
            menuSingleton.name[i] = cursor.getString(indexName)
            menuSingleton.description[i] = cursor.getString(indexDescription)
            menuSingleton.price[i] = cursor.getInt(indexPrice)

            i++
            cursor.moveToNext()
        }
    }

    fun getItemCount(category: Int): Int {
        cursor = db.rawQuery("SELECT * FROM ${MenuTable.TABLE_NAME} " +
                "WHERE ${MenuTable.COLUMN_GROUP_ID} = ?", arrayOf(category.toString()))
        Log.d("KOLVO", "${cursor.count}")
        return cursor.count
    }

}