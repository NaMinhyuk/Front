package com.example.lifesharing.mypage.model.response_body

import com.google.gson.annotations.SerializedName

data class QnaWaitResponse(
    @SerializedName("isSuccess") var isSuccess: Boolean?,
    @SerializedName("code") var code: String?=null,
    @SerializedName("message") var message: String?=null,
    @SerializedName("result") var result: InquiryResult
)

data class InquiryResult (
    @SerializedName("inquiryList") var inquiryList: List<InquiryItem>
)

data class InquiryItem (
    @SerializedName("inquiryId") var inquiryId: Long,
    @SerializedName("title") var title: String,
    @SerializedName("body") var body: String,
    @SerializedName("imageUrlList") var imageList: List<String>,
    @SerializedName("createdAt") var createdAt: String
)