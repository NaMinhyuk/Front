package com.example.lifesharing.mypage.mypage_api

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val UserInfoResultDTO : UserInfoResultDTO
)
data class UserInfoResultDTO(
    @SerializedName("userId") val userId: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("area") val area: String,
    @SerializedName("score") val score: String,
    @SerializedName("profileUrl") val profileUrl: String,
    @SerializedName("point") val point: Int
)
