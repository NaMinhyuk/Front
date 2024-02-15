package com.example.lifesharing.mypage.mypage_api

import com.google.gson.annotations.SerializedName

data class ViewInquiryAnswerResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("ViewInquiryResultList") val viewInquiryResultList: ViewInquiryResultList,
)

data class ViewInquiryResultList(
    @SerializedName("ViewInquiryResultList") val viewInquiryResult: List<ViewInquiryResult>,
    @SerializedName("ViewInquiryResultList") val viewInquiryReply: List<ViewInquiryReply>,
)

data class ViewInquiryResult(
    @SerializedName("id") val id : Long,
    @SerializedName("state") val state : String,
    @SerializedName("title") val title : Boolean,
    @SerializedName("body") val Body : String,
    @SerializedName("InquiryImageList") val InquiryImageList : String,
)

data class ViewInquiryReply(
    @SerializedName("id") val id : Long,
    @SerializedName("body") val body : Long,
)