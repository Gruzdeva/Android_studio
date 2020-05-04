package com.example.app2.ui.order

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.app2.DBReader
import com.example.app2.DBReader.OrdersTable
import com.example.app2.UserProfile

class TableOrders(context: Context) {
    val dbHelper = DBHelperAllOrders(context)
    val db = dbHelper.writableDatabase

    val userProfile = UserProfile.getInstance()

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

    fun deleteFromDB(){
        val cursor = db.rawQuery("DELETE FROM ${OrdersTable.TABLE_NAME}", null)
        cursor.moveToFirst()
    }
}