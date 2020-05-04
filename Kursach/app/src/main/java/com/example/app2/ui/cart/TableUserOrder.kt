package com.example.app2.ui.cart

import android.content.ContentValues
import android.content.Context
import com.example.app2.DBReader.UserOrderTable

class TableUserOrder(context: Context) {
    private val dbHelper = DBHelperUserOrder(context)
    private val db = dbHelper.writableDatabase

    var cursor = db.query(UserOrderTable.TABLE_NAME, null, null, null, null , null, null)

    val indexId = cursor.getColumnIndex(UserOrderTable.COLUMN_ID)
    val indexName = cursor.getColumnIndex(UserOrderTable.COLUMN_NAME)
    val indexPrice = cursor.getColumnIndex(UserOrderTable.COLUMN_PRICE)

    fun add_position(name: String, price: Int){
        val cv = ContentValues().apply {
            put(UserOrderTable.COLUMN_NAME, name)
            put(UserOrderTable.COLUMN_PRICE, price)
        }

        db.insert(UserOrderTable.TABLE_NAME, null, cv)
    }

    fun order_cost(): Int{
        cursor = db.rawQuery("SELECT SUM(${UserOrderTable.COLUMN_PRICE}) " +
                "FROM ${UserOrderTable.TABLE_NAME}", null)
        cursor.moveToFirst()

        return cursor.getInt(0)
    }

    fun delete_db_data(){
        cursor = db.rawQuery("DELETE FROM ${UserOrderTable.TABLE_NAME}", null)
        cursor.moveToFirst()
    }
}