package com.example.lifesharing.mypage.review.model.response_body

import com.google.gson.annotations.SerializedName

data class GetReviewResponseBody(
    @SerializedName("isSuccess") var isSuccess: Boolean?,
    @SerializedName("code") var code: String?=null,
    @SerializedName("message") var message: String?=null,
    @SerializedName("result") var result: ReviewResult
)

data class ReviewResult(
    @SerializedName("reviewCount") var reviewCount: Int?=null,
    @SerializedName("reviewListDTOList") var reviewListDTOList : ArrayList<ReviewListDTOList>
)

data class ReviewListDTOList(
    @SerializedName("reviewId") var reviewId: Int?=null,
    @SerializedName("userId") var userId: Int?=null,
    @SerializedName("nickName") var nickName: String?=null,
    @SerializedName("profileUrl") var profileUrl: String?=null,
    @SerializedName("createdAt") var createdAt: String?=null,
    @SerializedName("lentDay") var lentDay: String?=null,
    @SerializedName("imageList") var imageList: List<String>?=null,
    @SerializedName("score") var score: Int?=null,
    @SerializedName("content") var content: String?=null
)
