package com.example.app2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.app2.FeedReaderContract.FeedEntry

class DataBaseHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    private val context = context
    companion object {//if change schema? increment db version!!
        private val DB_NAME = "Sun.db"
        private val DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
       db!!.execSQL("CREATE TABLE ${FeedEntry.TABLE_NAME} (" +
               "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT" +
               "${FeedEntry.COLUmN_NAME_LOGIN} TEXT," +
               "${FeedEntry.COLUmN_NAME_PASSWORD} TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS ${FeedEntry.TABLE_NAME}")
        onCreate(db)
    }
}

