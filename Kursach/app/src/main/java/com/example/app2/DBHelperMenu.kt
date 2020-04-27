package com.example.app2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class DBHelperMenu(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    val context = context
    companion object{
        val DB_NAME = "Menu.db"
        var DB_PATH = ""
        val DB_VERSION = 1
    }

    init{
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.applicationInfo.dataDir + "/databases/"
        else
            DB_PATH = "/data/data/" + context.packageName + "/databases/"

        createDatabase()
    }

    fun checkDB(): Boolean{
        val dbFile = File(DB_PATH + DB_NAME)
        return dbFile.exists()
    }

    fun createDatabase(){
        if(!checkDB()) {
            this.readableDatabase
            this.close()

            try {
                copyDBFile()
            } catch(e: IOException){
                throw Error("ErrorCopyingDataBase")
            }
        }
    }

    @Throws(IOException::class)
    private fun copyDBFile() {
        val input = context.getAssets().open(DB_NAME)
        val output= FileOutputStream(DB_PATH + DB_NAME)
        val buffer = ByteArray(1024)
        var length: Int

        while (input.read(buffer).also { length = it } > 0)
            output.write(buffer, 0, length)

        output.flush()
        output.close()
        input.close()
    }

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}