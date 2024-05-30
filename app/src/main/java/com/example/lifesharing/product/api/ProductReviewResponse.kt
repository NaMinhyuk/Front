package com.example.lifesharing.product.api

import com.example.lifesharing.product.data.MyItemDetailResultDTO
import com.google.gson.annotations.SerializedName

data class ProductReviewResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ProductReviewResult
)

data class ProductReviewResult(
    @SerializedName("reviewCount") val reviewCount: Int,
    @SerializedName("productReviewDTOList") val productReviewDTOList: List<ProductReview>,
)

data class ProductReview(
    @SerializedName("reviewId") val reviewId: Long,
    @SerializedName("userId") val userId: Long,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("profileUrl") val profileUrl: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("lentDay") val lentDay: String,
    @SerializedName("imageList") val imageList: List<String>,
    @SerializedName("score") val score: Int,
    @SerializedName("content") val content: String
)