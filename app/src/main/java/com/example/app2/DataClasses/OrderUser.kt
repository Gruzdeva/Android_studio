package com.example.app2.DataClasses

import com.google.firebase.database.Exclude

data class OrderUser(
    val name: String? = "",
    val price: Int = 0
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "price" to price
        )
    }
}