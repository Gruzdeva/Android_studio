package com.example.app2.DBHelpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.app2.DBHelpers.DBReader.OrdersTable

class DBHelperAllOrders(context: Context): SQLiteOpenHelper(context,
    DB_NAME, null,
    DB_VERSION
) {
    companion object{
        val DB_NAME = "Orders.db"
        val DB_VERSION = 2
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE ${OrdersTable.TABLE_NAME} (" +
                "${OrdersTable.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${OrdersTable.COLUMN_USER_ID} INTEGER," +
                "${OrdersTable.COLUMN_NUMBER} INTEGER," +
                "${OrdersTable.COLUMN_COST} INTEGER);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}