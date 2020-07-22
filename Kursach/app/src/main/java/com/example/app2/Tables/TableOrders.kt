package com.example.app2.Tables

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.app2.DBHelpers.DBReader.OrdersTable
import com.example.app2.Singletons.UserProfile
import com.example.app2.DBHelpers.DBHelperAllOrders
import com.example.app2.Singletons.OrderSingleton

class TableOrders(context: Context) {
    val dbHelper = DBHelperAllOrders(context)
    val db = dbHelper.writableDatabase

    val userProfile = UserProfile.getInstance()
    val orderSingleton = OrderSingleton.getInstance()!!

    var cursor = db.query(OrdersTable.TABLE_NAME, null, null, null, null , null, null)

    val indexId = cursor.getColumnIndex(OrdersTable.COLUMN_ID)
    val indexUserId = cursor.getColumnIndex(OrdersTable.COLUMN_USER_ID)
    val indexNumber = cursor.getColumnIndex(OrdersTable.COLUMN_NUMBER)
    val indexCost = cursor.getColumnIndex(OrdersTable.COLUMN_COST)

    fun addNewOrder(cost: Int){
        cursor = db.rawQuery("SELECT MAX(${OrdersTable.COLUMN_NUMBER}) " +
                "FROM ${OrdersTable.TABLE_NAME} " +
                "WHERE ${OrdersTable.COLUMN_USER_ID} = ?", arrayOf(userProfile.id.toString()))
        cursor.moveToNext()

        var numberVal: Int
        if(cursor.count == 0){
            numberVal = 0
        } else {
            numberVal = cursor.getInt(0) + 1
        }

        val cv = ContentValues().apply {
            put(OrdersTable.COLUMN_USER_ID, userProfile.id)
            put(OrdersTable.COLUMN_NUMBER, numberVal)
            put(OrdersTable.COLUMN_COST, cost)
        }

        db.insert(OrdersTable.TABLE_NAME, null, cv)

    }

    fun load_in_singleton(){
        cursor = db.rawQuery("SELECT * FROM ${OrdersTable.TABLE_NAME} " +
                "WHERE ${OrdersTable.COLUMN_USER_ID} = ?", arrayOf(userProfile.id.toString()))
        cursor.moveToFirst()

        var i = 0
        while(!cursor.isAfterLast){
            orderSingleton.numbers[i] = cursor.getInt(2)
            orderSingleton.costs[i] = cursor.getInt(3)

            i++
            cursor.moveToNext()
        }
    }

    fun deleteFromDB(){
        cursor = db.rawQuery("DELETE FROM ${OrdersTable.TABLE_NAME}", null)
        cursor.moveToFirst()

        cursor = db.rawQuery("UPDATE sqlite_sequence " +
                "SET seq = 0 " +
                "WHERE Name = ?", arrayOf(OrdersTable.TABLE_NAME))
        cursor.moveToFirst()
    }

    fun itemCount(): Int{
        cursor = db.rawQuery("SELECT COUNT() FROM ${OrdersTable.TABLE_NAME} " +
                "WHERE ${OrdersTable.COLUMN_USER_ID} = ?", arrayOf(userProfile.id.toString()))
        cursor.moveToFirst()

        return cursor.getInt(0)
    }

    fun tableInfo() {
        cursor = db.rawQuery("SELECT * FROM ${OrdersTable.TABLE_NAME}", null)
        cursor.moveToFirst()

        while (!cursor.isAfterLast) {
            Log.d("TABLEINFO", "${cursor.getInt(indexId)} ${cursor.getString(indexUserId)} " +
                    "${cursor.getString(indexNumber)} ${cursor.getInt(indexCost)}")

            cursor.moveToNext()
        }
    }
}