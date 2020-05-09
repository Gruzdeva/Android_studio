package com.example.app2.ui.cart

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.app2.DBReader
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
        cursor = db.rawQuery("UPDATE sqlite_sequence " +
                "SET seq = 0 " +
                "WHERE Name = ?", arrayOf(UserOrderTable.TABLE_NAME))
        cursor.moveToFirst()
    }

    fun deletePosition(position: Int){
        cursor = db.rawQuery("DELETE FROM ${UserOrderTable.TABLE_NAME} " +
                "WHERE ${UserOrderTable.COLUMN_ID} = ?", arrayOf(position.toString()))
        cursor.moveToFirst()

//        cursor = db.rawQuery("SELECT * FROM ${UserOrderTable.TABLE_NAME}", null)
//        cursor.moveToFirst()
//        val size = cursor.count
//        var names: ArrayList<String> = arrayListOf()
//        var prices: ArrayList<Int> = arrayListOf()
//
//
//        while (!cursor.isAfterLast){
//            names.add(cursor.getString(indexName))
//            prices.add(cursor.getInt(indexPrice))
//
//            cursor.moveToNext()
//        }
//
//        delete_db_data()
//
//        for (x in 0..size - 1){
//            val cv = ContentValues().apply {
//                put(UserOrderTable.COLUMN_NAME, names[x])
//                put(UserOrderTable.COLUMN_PRICE, prices[x])
//            }
//
//            db.insert(UserOrderTable.TABLE_NAME, null, cv)
//        }
    }

    fun tableInfo(){
        cursor = db.rawQuery("SELECT * FROM ${UserOrderTable.TABLE_NAME}", null)
        cursor.moveToFirst()

        while(!cursor.isAfterLast){
            Log.d("TABLEINFO", "${cursor.getInt(indexId)} " +
                    "${cursor.getString(indexName)} ${cursor.getString(indexPrice)}")

            cursor.moveToNext()
        }
    }
}