package com.example.lifesharing.mypage.mypage_api

import com.google.gson.annotations.SerializedName

data class ViewWishListResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("HeartResultList") val heartResultList: HeartResultList,
)

data class HeartResultList(
    @SerializedName("HeartResultList") val heartResult: HeartResult,
)

data class HeartResult(
    @SerializedName("productId") val productId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: String,
    @SerializedName("deposit") val deposit: Int,
    @SerializedName("dayPrice") val dayPrice: Int,
    @SerializedName("score") val score: Int,
    @SerializedName("reviewCount") val reviewCount: Int,
    @SerializedName("image_url") val image_url: String?=null,
)