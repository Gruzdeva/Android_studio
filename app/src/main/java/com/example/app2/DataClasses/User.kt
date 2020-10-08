package com.example.app2.DataClasses

import com.google.firebase.database.Exclude

data class User(
    val uId: String? = "",
    val email: String? = "",
    val name: String? = "",
    val points: Int = 0 ) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uId" to uId,
            "email" to email,
            "name" to name,
            "points" to points
        )
    }
}