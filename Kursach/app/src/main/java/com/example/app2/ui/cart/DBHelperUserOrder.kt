package com.example.app2.ui.cart

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.app2.DBReader.UserOrderTable

class DBHelperUserOrder(context: Context): SQLiteOpenHelper(context,
    DB_NAME, null,
    DB_VERSION
) {
    companion object{
        val DB_NAME = "UserOrder.db"
        val DB_VERSION = 1
    }
    val context = context
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE ${UserOrderTable.TABLE_NAME} (" +
                "${UserOrderTable.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${UserOrderTable.COLUMN_NAME} TEXT," +
                "${UserOrderTable.COLUMN_PRICE} INTEGER);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}