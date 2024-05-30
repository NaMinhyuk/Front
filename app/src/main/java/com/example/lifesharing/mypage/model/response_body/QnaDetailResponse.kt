package com.example.lifesharing.mypage.model.response_body

import com.google.gson.annotations.SerializedName

data class QnaDetailResponse(
    @SerializedName("isSuccess") var isSuccess: Boolean?,
    @SerializedName("code") var code: String?=null,
    @SerializedName("message") var message: String?=null,
    @SerializedName("result") var result: InquiryDetailResult
)

data class InquiryDetailResult(
    @SerializedName("inquiry") var inquiry: Inquiry,
    @SerializedName("reply") var reply: ReplyResult
)

data class Inquiry(
    @SerializedName("id") var inquiryId: Long,
    @SerializedName("state") var state: String,
    @SerializedName("title") var title: String,
    @SerializedName("body") var body: String,
    @SerializedName("inquiryImageList") var imageList: List<InquiryImage>
)

data class InquiryImage(
    @SerializedName("id") val id: Long,
    @SerializedName("imageUrl") val imageUrl: String
)

data class ReplyResult(
    @SerializedName("id") var replyId: Long,
    @SerializedName("body") var replyBody: String,
)
