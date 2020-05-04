package com.example.app2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.app2.DBReader.UserTable

class DBHelperUsers(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    val context = context
    companion object{
        val DB_NAME = "Test.db"
        val DB_VERSION = 2
    }

    override fun onCreate(db: SQLiteDatabase?) =
        db!!.execSQL("CREATE TABLE ${UserTable.TABLE_NAME} (" +
                "${UserTable.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${UserTable.COLUMN_LOGIN} TEXT," +
                "${UserTable.COLUMN_NAME} TEXT," +
                "${UserTable.COLUMN_PASSWORD} TEXT," +
                "${UserTable.COLUMN_POINTS} INTEGER," +
                "${UserTable.COLUMN_REMEMBER} TEXT);")

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}