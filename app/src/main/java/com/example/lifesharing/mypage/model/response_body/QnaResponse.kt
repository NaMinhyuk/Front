package com.example.lifesharing.mypage.model.response_body

import com.google.gson.annotations.SerializedName

data class QnaResponse(
    @SerializedName("isSuccess") var isSuccess: Boolean?,
    @SerializedName("code") var code: String?=null,
    @SerializedName("message") var message: String?=null,
    @SerializedName("result") var result: QnaWriteResult
)

data class QnaWriteResult (
    @SerializedName("inquiryId") var inquiryId : Long,
    @SerializedName("title") var title : String,
    @SerializedName("body") var body : String,
    @SerializedName("imageUrlList") var imageList : List<String> ?= null,
    @SerializedName("createdAt") var createdAt : String
)
