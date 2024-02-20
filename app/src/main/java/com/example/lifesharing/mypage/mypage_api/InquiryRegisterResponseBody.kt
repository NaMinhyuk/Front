package com.example.lifesharing.mypage.mypage_api

import com.google.gson.annotations.SerializedName
import java.util.Date

data class InquiryRegisterResponseBody(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: QnaRegisterResultList,
)

data class QnaRegisterResultList (
    @SerializedName("inquiryId") val inquiryId : Long,
    @SerializedName("title") val title : String,
    @SerializedName("body") val body : String,
    @SerializedName("imageUrlList") val imageUrlList : List<String>,
    @SerializedName("createAt") val createAt: Date,
)
