package com.example.lifesharing.mypage.model.response_body

import com.google.gson.annotations.SerializedName

data class RegisterHistoryResponse(
    @SerializedName("isSuccess") var isSuccess: Boolean?,
    @SerializedName("code") var code: String?=null,
    @SerializedName("message") var message: String?=null,
    @SerializedName("result") var result: ResultList
)

data class ResultList (
    @SerializedName("productCount") var productCount: Int,
    @SerializedName("myRegProductList") var myRegProductList: List<MyRegProductList>
)

data class MyRegProductList (
    @SerializedName("productId") var productId: Long,
    @SerializedName("imageUrl") var imageUrl: String?=null,
    @SerializedName("location") var location: String,
    @SerializedName("name") var name: String,
    @SerializedName("startDate") var startDate: String,
    @SerializedName("endDate") var endDate: String
)
