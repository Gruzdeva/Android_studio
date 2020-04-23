package com.example.app2

import android.provider.BaseColumns

object FeedReaderContract {
    object UserTable: BaseColumns{
        const val TABLE_NAME = "Users"
        const val COLUMN_ID = "user_id"
        const val COLUMN_LOGIN = "login"
        const val COLUMN_NAME = "name"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_POINTS = "points"
    }
}