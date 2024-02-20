package com.example.lifesharing.mypage.mypage_api

import com.google.gson.annotations.SerializedName

data class InquiryRegisterRequestBody(
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String,
)
