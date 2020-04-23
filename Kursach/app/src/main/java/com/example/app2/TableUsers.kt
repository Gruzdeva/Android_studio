package com.example.app2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.app2.FeedReaderContract.UserTable

class TableUsers(context: Context) {
    private val dbHelper = DBHelperUsers(context)
    private val db = dbHelper.writableDatabase

    val cursor = db.query(UserTable.TABLE_NAME, null, null, null, null , null, null)

    private var indexId = cursor.getColumnIndex(UserTable.COLUMN_ID)
    private var indexLogin = cursor.getColumnIndex(UserTable.COLUMN_LOGIN)
    private var indexName = cursor.getColumnIndex(UserTable.COLUMN_NAME)
    private var indexPassword = cursor.getColumnIndex(UserTable.COLUMN_PASSWORD)
    private var indexPoints = cursor.getColumnIndex(UserTable.COLUMN_POINTS)

    private var userProfile = UserProfile.getInstance()

    fun signIn(){
        val cursor = db.rawQuery("SELECT * FROM ${UserTable.TABLE_NAME}" +
                "WHERE ${UserTable.COLUMN_LOGIN} = ? AND " +
                "${UserTable.COLUMN_PASSWORD} = ?",
            arrayOf(userProfile.login, userProfile.password))
        cursor.moveToFirst()

        userProfile.id = cursor.getInt(indexId)
        userProfile.name = cursor.getString(indexName)
        userProfile.points = cursor.getInt(indexPoints)

        cursor.close()
        db.close()
    }

    fun signUp(){
        val values = ContentValues().apply {
            put(UserTable.COLUMN_LOGIN, userProfile.login)
            put(UserTable.COLUMN_NAME, userProfile.name)
            put(UserTable.COLUMN_PASSWORD, userProfile.password)
            put(UserTable.COLUMN_POINTS, userProfile.points)
        }

        db.insert(UserTable.TABLE_NAME, null, values)

        //request id
        val cursor = db.rawQuery("SELECT ${UserTable.COLUMN_ID}" +
                "FROM ${UserTable.TABLE_NAME} WHERE ${UserTable.COLUMN_LOGIN} = ?",
            arrayOf(userProfile.login))
        cursor.moveToFirst()

        userProfile.id = cursor.getInt(indexId)

        cursor.close()
        db.close()
    }

    fun logOut(){// add later

    }

    fun checkLogin(login: String): Boolean{
        val cursor = db.rawQuery("SELECT * FROM ${UserTable.TABLE_NAME}" +
                "WHERE ${UserTable.COLUMN_LOGIN} = ?", arrayOf(login))
        cursor.moveToFirst()

        if(cursor.count == 0){
            cursor.close()
            db.close()
            return false
        } else{
            cursor.close()
            db.close()
            return true
        }
    }

//    fun checkProfile(login: String, password: String): Boolean{
//        val cursor = db.rawQuery("SELECT * FROM ${UserTable.TABLE_NAME}" +
//                "WHERE ${UserTable.COLUMN_LOGIN} = ? AND " +
//                "${UserTable.COLUMN_PASSWORD} = ?", arrayOf(login, password))
//
//
//    }
}