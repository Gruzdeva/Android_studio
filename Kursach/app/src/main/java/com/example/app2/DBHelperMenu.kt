package com.example.app2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.*
import java.sql.SQLException


class DBHelperMenu(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    val context = context
    companion object{
        val DB_NAME = "Menu.db"
        var DB_PATH = ""
        val DB_VERSION = 1
    }

    init{
        DB_PATH =context.getFilesDir().getPath() + DB_NAME
    }

    fun create_db(){
        var myInput: InputStream? = null
        var myOutput: OutputStream? = null
        try {
            val file = File(DB_PATH)
            if (!file.exists()) {
                this.readableDatabase
                //получаем локальную бд как поток
                myInput = context.getAssets().open(DB_NAME)
                // Путь к новой бд
                val outFileName = DB_PATH

                // Открываем пустую бд
                myOutput = FileOutputStream(outFileName)

                // побайтово копируем данные
                val buffer = ByteArray(1024)
                var length: Int = 0
                while (myInput.read(buffer).also({ length = it }) > 0) {
                    myOutput.write(buffer, 0, length)
                }
                myOutput.flush()
                myOutput.close()
                myInput.close()
            }
        } catch (ex: IOException) {
            Log.d("DatabaseHelper", ex.message)
        }
    }

    @Throws(SQLException::class)
    fun open(): SQLiteDatabase? {
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE)
    }

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}