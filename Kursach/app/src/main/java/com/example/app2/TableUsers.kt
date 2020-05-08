package com.example.app2

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.app2.DBReader.UserTable

class TableUsers(context: Context) {

    private val dbHelper = DBHelperUsers(context)
    private val db = dbHelper.writableDatabase

    val cursor = db.query(UserTable.TABLE_NAME, null, null, null, null , null, null)

    private var indexId = cursor.getColumnIndex(UserTable.COLUMN_ID)
    private var indexLogin = cursor.getColumnIndex(UserTable.COLUMN_LOGIN)
    private var indexName = cursor.getColumnIndex(UserTable.COLUMN_NAME)
    private var indexPassword = cursor.getColumnIndex(UserTable.COLUMN_PASSWORD)
    private var indexPoints = cursor.getColumnIndex(UserTable.COLUMN_POINTS)
    private var indexRemember = cursor.getColumnIndex(UserTable.COLUMN_REMEMBER)

    private var userProfile = UserProfile.getInstance()

    fun signIn(login: String, password: String){
        val cursor = db.rawQuery("SELECT * FROM ${UserTable.TABLE_NAME} " +
                "WHERE ${UserTable.COLUMN_LOGIN} = ? AND " +
                "${UserTable.COLUMN_PASSWORD} = ? ",
            arrayOf(login, password))
        cursor.moveToFirst()

        userProfile.id = cursor.getInt(indexId)
        userProfile.name = cursor.getString(indexName)
        userProfile.points = cursor.getInt(indexPoints)

        val cv = ContentValues().apply {
            put(UserTable.COLUMN_REMEMBER, 1)
        }
        db.update(UserTable.TABLE_NAME, cv, UserTable.COLUMN_ID + "=" + cursor.getInt(indexId).toString(),null)

//        cursor.close()
//        db.close()
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
        val cursor = db.rawQuery("SELECT ${UserTable.COLUMN_ID} " +
                "FROM ${UserTable.TABLE_NAME} WHERE ${UserTable.COLUMN_LOGIN} = ? ",
            arrayOf(userProfile.login))
        cursor.moveToFirst()

        userProfile.id = cursor.getInt(indexId)

        val cv = ContentValues().apply {
            put(UserTable.COLUMN_REMEMBER, 1)
        }
        db.update(UserTable.TABLE_NAME, cv, UserTable.COLUMN_ID + "=" + userProfile.id.toString(),null)

//        cursor.close()
//        db.close()
    }

    fun logOut(){// add later
        val cv = ContentValues().apply {
            put(UserTable.COLUMN_REMEMBER, 0)
        }
        db.update(UserTable.TABLE_NAME, cv, UserTable.COLUMN_ID + "=" + userProfile.id.toString(),null)
    }

    fun checkLogin(login: String): Boolean {
        val cursor = db.rawQuery("SELECT * FROM ${UserTable.TABLE_NAME} " +
                "WHERE ${UserTable.COLUMN_LOGIN} = ? ", arrayOf(login))
        cursor.moveToFirst()

        return cursor.count != 0
    }

    fun checkProfile(login: String, password: String): Boolean {
        val cursor = db.rawQuery("SELECT * FROM ${UserTable.TABLE_NAME} " +
                "WHERE ${UserTable.COLUMN_LOGIN} = ? AND " +
                "${UserTable.COLUMN_PASSWORD} = ? ", arrayOf(login, password))
        cursor.moveToFirst()

        return cursor.count != 0
    }

    fun checkRemember(): Boolean {
        val cursor = db.rawQuery("SELECT * FROM ${UserTable.TABLE_NAME} " +
                "WHERE ${UserTable.COLUMN_REMEMBER} = ? ", arrayOf("1"))
        cursor.moveToFirst()

        return cursor.count != 0
    }

    fun loadRemember(){
        val cursor = db.rawQuery("SELECT * FROM ${UserTable.TABLE_NAME} " +
                "WHERE ${UserTable.COLUMN_REMEMBER} = ? ", arrayOf("1"))
        cursor.moveToFirst()

        userProfile.id = cursor.getInt(indexId)
        userProfile.login = cursor.getString(indexLogin)
        userProfile.name = cursor.getString(indexName)
        userProfile.password = cursor.getString(indexPassword)
        userProfile.points = cursor.getInt(indexPoints)

//        cursor.close()
//        db.close()
    }

    fun checkForgot(login: String, name: String): Boolean{ //maybe need modify
        val cursor = db.rawQuery("SELECT * FROM ${UserTable.TABLE_NAME} " +
                "WHERE ${UserTable.COLUMN_LOGIN} = ? AND " +
                "${UserTable.COLUMN_NAME} = ? ", arrayOf(login, name))
        cursor.moveToFirst()

        return cursor.count != 0
    }

    fun getPass(login: String, name: String): String{
        val cursor = db.rawQuery("SELECT ${UserTable.COLUMN_PASSWORD} FROM ${UserTable.TABLE_NAME} " +
                "WHERE ${UserTable.COLUMN_LOGIN} = ? AND " +
                "${UserTable.COLUMN_NAME} = ? ", arrayOf(login, name))
        cursor.moveToFirst()

        return cursor.getString(0)
    }

    fun deleteFromTable(){// delete later!
        val cursor = db.rawQuery("DELETE FROM ${UserTable.TABLE_NAME}", null)
        cursor.moveToFirst()
    }

    fun tableInfo(){
        val cursor = db.rawQuery("SELECT * FROM ${UserTable.TABLE_NAME}", null)
        cursor.moveToFirst()

        while(!cursor.isAfterLast){
            Log.d("TABLEINFO", "${cursor.getInt(indexId)} " +
                    "${cursor.getString(indexLogin)} ${cursor.getString(indexName)} " +
                    "${cursor.getString(indexPassword)} ${cursor.getInt(indexPoints)} " +
                    "${cursor.getString(indexRemember)}" )

            cursor.moveToNext()
        }
    }

    fun updatePoints(points: Int){
        val cv = ContentValues().apply {
            put(UserTable.COLUMN_POINTS, points)
        }

        db.update(UserTable.TABLE_NAME, cv, UserTable.COLUMN_ID + "=" + userProfile.id.toString(),null)

    }

    fun close(){
        dbHelper.close()
    }
}