package com.example.lifesharing.profile.model.response_body

import com.google.gson.annotations.SerializedName

data class SellerReviewResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("code") var code: String?,
    @SerializedName("message") var message: String,
    @SerializedName("result") var result: ReviewListResult?
)

data class ReviewListResult(
    @SerializedName("reviewList") var reviewList: List<ReviewData>?
)

data class ReviewData(
    @SerializedName("reviewId") var reviewId: Long,
    @SerializedName("userId") var userId: Long,
    @SerializedName("nickName") var nickName: String,
    @SerializedName("profileUrl") var profileUrl: String,
    @SerializedName("createdAt") var createdAt: String,
    @SerializedName("lentDay") var lentDay: String,
    @SerializedName("imageList") var imageList: List<String>?,
    @SerializedName("score") var score: Int,
    @SerializedName("content") var content: String,
)