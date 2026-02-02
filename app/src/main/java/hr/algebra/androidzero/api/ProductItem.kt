package hr.algebra.androidzero.api

import com.google.gson.annotations.SerializedName

data class ProductItem(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Double,
    @SerializedName("description") val explanation: String, // 'description' iz JSON-a postaje 'explanation'
    @SerializedName("image") val url: String,               // 'image' iz JSON-a postaje 'url'
    @SerializedName("category") val category: String
)