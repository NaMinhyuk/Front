package com.example.lifesharing.mypage.mypage_api

import com.google.gson.annotations.SerializedName

data class ViewInquiryAnswerResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ViewInquiryResult,
)

data class ViewInquiryResult(
    @SerializedName("inquiry") val inquiry: List<ViewInquiry>,
    @SerializedName("reply") val reply: List<ViewInquiryReply>,
)

data class ViewInquiry(
    @SerializedName("id") val id : Long,
    @SerializedName("state") val state : String,
    @SerializedName("title") val title : String,
    @SerializedName("body") val body : String,
    @SerializedName("InquiryImageList") val InquiryImageList : String,
)

data class ViewInquiryReply(
    @SerializedName("id") val id : Long,
    @SerializedName("body") val body : String,
)