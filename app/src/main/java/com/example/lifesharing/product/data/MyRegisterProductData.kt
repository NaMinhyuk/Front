package com.example.lifesharing.product.data

import com.google.gson.annotations.SerializedName

data class MyRegisterProductData (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val myProductResultDTO: List<myProductResultDTO>,
)

data class myProductResultDTO(
    @SerializedName("product_id") val productId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: String,
    @SerializedName("deposit") val deposit: Int,
    @SerializedName("day_price") val dayPrice: Int,
    @SerializedName("score") val score: Int,
    @SerializedName("reviewCount") val reviewCount: Int,
    @SerializedName("image_url") val imageUrl: String?=null
)