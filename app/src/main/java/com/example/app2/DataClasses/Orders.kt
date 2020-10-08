package com.example.app2.DataClasses

import com.google.firebase.database.Exclude
import com.google.gson.annotations.SerializedName

data class Orders(
    @field:SerializedName("uId")
    val uId: String? = null,
    @field:SerializedName("orderDate")
    val orderDate: String? = null,
    @field:SerializedName("orderPrice")
    val orderPrice: Int? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uId" to uId,
            "orderDate" to orderDate,
            "orderPrice" to orderPrice
        )
    }
}