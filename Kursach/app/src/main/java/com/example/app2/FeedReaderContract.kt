package com.example.app2

import android.provider.BaseColumns

object FeedReaderContract {
    object FeedEntry: BaseColumns{
        const val TABLE_NAME = "Users"
        const val COLUmN_NAME_LOGIN = "login"
        const val COLUmN_NAME_PASSWORD = "password"
    }
}