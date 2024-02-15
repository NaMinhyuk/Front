package com.example.lifesharing.mypage.mypage_api

import com.google.gson.annotations.SerializedName
class InquiryResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("InquiryResultList") val inquiryResultList: InquiryResultList,
)

data class InquiryResultList(
    @SerializedName("InquiryResultList") val inquiryResult: List<InquiryResult>,
)

data class InquiryResult(
    @SerializedName("size") val size : Int,
    @SerializedName("hasNext") val hasNext : Boolean,
)