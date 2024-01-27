package com.example.lifesharing.home.home_data

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("productId") val productId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: String,
    @SerializedName("deposit") val deposit: Int,
    @SerializedName("dayPrice") val dayPrice: Int,
    @SerializedName("score") val score: Int?,
    @SerializedName("reviewCount") val reviewCount: Int,
    @SerializedName("image_url") val imageUrl: String?
)

data class ProductResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ProductResult
)

data class ProductResult(
    @SerializedName("productResultDTOList") val productResultDTOList: List<Product>
)
