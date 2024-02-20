package com.example.lifesharing.mypage.mypage_api

import com.google.gson.annotations.SerializedName
import java.util.Date

class InquiryResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: InquiryResult,
)

data class InquiryResult(
    @SerializedName("size") var size:Int?=null,
    @SerializedName("hasNext") var hasNext:Boolean?=null,
    @SerializedName("inquiryList") var inquiryList : ArrayList<InquiryDTO>
)

data class InquiryDTO(
    @SerializedName("inquiryId") var inquiryId: Int?=null,
    @SerializedName("title") var title: String?=null,
    @SerializedName("body") var body: String?=null,
    @SerializedName("imageUrlList") var imageUrlList: List<String>?=null,
    @SerializedName("createdAt") var createdAt: String?=null
)