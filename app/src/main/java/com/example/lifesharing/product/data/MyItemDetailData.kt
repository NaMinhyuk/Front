package com.example.lifesharing.product.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class MyItemDetailData(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val myProductResultDTO: MyItemDetailResultDTO
)

data class MyItemDetailResultDTO(
    @SerializedName("productId") val productId: Long,
    @SerializedName("userId") val userId: Long,
    @SerializedName("categoryList") val categoryList: List<String>,
    @SerializedName("location") val location: String,
    @SerializedName("detailLocation") val detailLocation: String,
    @SerializedName("name") val name: String,
    @SerializedName("imageUrl") val imageUrl: List<String>?=null,
    @SerializedName("score") val score: Int,
    @SerializedName("reviewCount") val reviewCount: Int,
    @SerializedName("deposit") val deposit: Int,
    @SerializedName("dayPrice") val dayPrice: Int,
    @SerializedName("hourPrice") val hourPrice: Int,
    @SerializedName("isLiked") var isLiked: Boolean,
    @SerializedName("content") val content: String,
    @SerializedName("userNickname") val nickname: String,
    @SerializedName("userImage") val userImage: String?=null,
)


