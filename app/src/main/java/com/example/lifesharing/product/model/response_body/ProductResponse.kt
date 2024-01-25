package com.example.lifesharing.product.model.response_body

import com.google.gson.annotations.SerializedName

data class ApiResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Product
)

data class Product (
    @SerializedName("productId") val productId: Long,
    @SerializedName("categoryId") val categoryId: Long,
    @SerializedName("userId") val userId: Long?,
    @SerializedName("categoryName") val categoryName: String,
    @SerializedName("location") val location: String,
    @SerializedName("imageUrl") val imageUrl: List<String>,
    @SerializedName("name") val name: String,
    @SerializedName("score") val score: Int,
    @SerializedName("reviewCount") val reviewCount: Int,
    @SerializedName("deposit") val deposit: Int,
    @SerializedName("dayPrice") val dayPrice: Int,
    @SerializedName("hourPrice") val hourPrice: Int,
    @SerializedName("isLiked") val isLiked: Boolean,
    @SerializedName("content") val content: String,
    @SerializedName("userNickname") val userNickname: String,
    @SerializedName("userImage") val userImage: String?,
    @SerializedName("reviewList") val reviewList: List<Review>
)

data class Review (
    @SerializedName("reviewId") val reviewId: Long,
    @SerializedName("userId") val userId: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("lentDay") val lentDay: String?,
    @SerializedName("imageList") val imageList: List<String>,
    @SerializedName("score") val score: Int,
    @SerializedName("content") val content: String
)
