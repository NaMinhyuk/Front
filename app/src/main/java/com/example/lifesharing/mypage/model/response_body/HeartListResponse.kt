package com.example.lifesharing.mypage.model.response_body

import com.google.gson.annotations.SerializedName

data class HeartListResponse(
    @SerializedName("isSuccess") var isSuccess: Boolean?,
    @SerializedName("code") var code: String?=null,
    @SerializedName("message") var message: String?=null,
    @SerializedName("result") var result: HeartResultList
)

data class HeartResultList (
    @SerializedName("heartResultList") var list: List<HeartList>
)

data class HeartList (
    @SerializedName("productId") var productId: Long,
    @SerializedName("name") var name: String,
    @SerializedName("location") var location: String,
    @SerializedName("deposit") var deposit: Int,
    @SerializedName("dayPrice") var dayPrice: Int,
    @SerializedName("score") var score: Int,
    @SerializedName("reviewCount") var reviewCount: Int,
    @SerializedName("imageUrl") var imageUrl: String?=null
)
