package com.example.app2.ui.order

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.app2.DBReader.OrdersTable
import com.example.app2.DBReader.UserTable

class DBHelperAllOrders(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object{
        val DB_NAME = "Orders.db"
        val DB_VERSION = 1
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE ${OrdersTable.TABLE_NAME} (" +
                "${OrdersTable.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${OrdersTable.COLUMN_USER_ID} INTEGER," +
                "${OrdersTable.COLUMN_NUMBER} INTEGER," +
                "${OrdersTable.COLUMN_COST} INTEGER," +
                "FOREIGN KEY (${OrdersTable.COLUMN_USER_ID}) REFERENCES ${UserTable.TABLE_NAME} (${UserTable.COLUMN_ID}));")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}