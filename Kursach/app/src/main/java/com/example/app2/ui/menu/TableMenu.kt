package com.example.app2.ui.menu

import android.content.Context
import com.example.app2.DBReader.MenuTable

class TableMenu(context: Context) {
    val dbHelper = DBHelperMenu(context)
    val db = dbHelper.writableDatabase

    val cursor = db.query(MenuTable.TABLE_NAME, null, null, null, null, null, null)

    val indexId = cursor.getColumnIndex(MenuTable.COLUMN_ID)
    val indexGroupId = cursor.getColumnIndex(MenuTable.COLUMN_GROUP_ID)
    val indexName = cursor.getColumnIndex(MenuTable.COLUMN_NAME)
    val indexDescription = cursor.getColumnIndex(MenuTable.COLUMN_DESCRIPTION)
    val indexPrice = cursor.getColumnIndex(MenuTable.COLUMN_PRICE)

    var menuInfo = MenuInfo.getInstance()

}