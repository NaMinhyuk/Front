package com.example.lifesharing.mypage.review.model.response_body

import com.google.gson.annotations.SerializedName

data class ReviewResponseBody(
    @SerializedName("isSuccess") var isSuccess: Boolean?,
    @SerializedName("code") var code: String?=null,
    @SerializedName("message") var message: String?=null,
    @SerializedName("result") var result: Review?
)

data class Review(
    @SerializedName("reviewId") var reviewId : Int?=null,
    @SerializedName("createdAt") var createdAt : String?=null
)
