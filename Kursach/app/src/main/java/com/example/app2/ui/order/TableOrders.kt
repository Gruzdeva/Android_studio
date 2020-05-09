package com.example.app2.ui.order

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.app2.DBReader
import com.example.app2.DBReader.OrdersTable
import com.example.app2.UserProfile

class TableOrders(context: Context) {
    val dbHelper = DBHelperAllOrders(context)
    val db = dbHelper.writableDatabase

    val userProfile = UserProfile.getInstance()
    val orderSingleton = OrderSingleton.getInstance()!!

    fun addNewOrder(cost: Int){
        val cursor = db.rawQuery("SELECT MAX(${OrdersTable.COLUMN_NUMBER}) " +
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
        val cursor = db.rawQuery("SELECT * FROM ${OrdersTable.TABLE_NAME} " +
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
        var cursor = db.rawQuery("DELETE FROM ${OrdersTable.TABLE_NAME}", null)
        cursor.moveToFirst()

        cursor = db.rawQuery("UPDATE sqlite_sequence " +
                "SET seq = 0 " +
                "WHERE Name = ?", arrayOf(OrdersTable.TABLE_NAME))
        cursor.moveToFirst()
    }

    fun itemCount(): Int{
        val cursor = db.rawQuery("SELECT COUNT() FROM ${OrdersTable.TABLE_NAME} " +
                "WHERE ${OrdersTable.COLUMN_USER_ID} = ?", arrayOf(userProfile.id.toString()))
        cursor.moveToFirst()

        return cursor.getInt(0)
    }
}