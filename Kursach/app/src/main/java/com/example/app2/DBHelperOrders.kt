package com.example.app2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.app2.DBReader.OrderTable
import com.example.app2.DBReader.UserTable

class DBHelperOrders(context: Context): SQLiteOpenHelper(context,
    DBHelperUsers.DB_NAME, null,
    DBHelperUsers.DB_VERSION
) {
    val context = context
    override fun onCreate(db: SQLiteDatabase?) {
//        db!!.execSQL("PRAGMA foreign_keys = on;")
//        db!!.execSQL("CREATE TABLE ${OrderTable.TABLE_NAME} (" +
//                "${OrderTable.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "${OrderTable.COLUMN_USER_ID} INTEGER," +
//                "${OrderTable.COLUMN_NUMBER} INTEGER" +
//                "${OrderTable.COLUMN_COST} INTEGER," +
//                "FOREIGN KEY (${OrderTable.COLUMN_USER_ID}) REFERENCES ${UserTable.TABLE_NAME}(${UserTable.COLUMN_ID}));")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}