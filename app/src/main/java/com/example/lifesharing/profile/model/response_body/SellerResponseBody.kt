package com.example.lifesharing.profile.model.response_body

import com.google.gson.annotations.SerializedName

data class SellerResponseBody(
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("code") var code: String?,
    @SerializedName("message") var message: String,
    @SerializedName("result") var result: UserProfileResult?= UserProfileResult(),
)

data class UserProfileResult (
    @SerializedName("userId") val userId: Long?=null,
    @SerializedName("userName") val userName: String?=null,
    @SerializedName("imageUrl") val imageUrl: String?=null,
    @SerializedName("location") val location: String?=null,
    @SerializedName("score") val score: Int?=null,
    @SerializedName("reviewCount") val reviewCount: Int?=null,
    @SerializedName("productCount") val productCount: Int?=null,
    @SerializedName("rentProductCnt") val rentProductCnt: Int?=null
)
