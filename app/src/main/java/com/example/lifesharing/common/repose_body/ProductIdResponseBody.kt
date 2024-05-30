package com.example.lifesharing.common.response_body

import com.example.lifesharing.messenger.model.response_body.MessengerRoomListTempResult
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class ProductIdResponseBody(
    @SerializedName("isSuccess") var isSuccess: Boolean?,
    @SerializedName("code") var code: String?=null,
    @SerializedName("message") var message: String?=null,
    @SerializedName("result") var result: Product?
)

data class Product(
    @SerializedName("productId") var productId: Long?=null,
    @SerializedName("categoryList") var categoryId: List<String>?=null,
    @SerializedName("userId") var userId: Long?=null,
    @SerializedName("location") var location: String?=null,
    @SerializedName("detailAddress") var detailAddress: String?=null,
    @SerializedName("imageUrl") var imageUrl: ArrayList<String>?=null,
    @SerializedName("name") var name: String?=null,
    @SerializedName("score") var score: Int?=null,
    @SerializedName("reviewCount") var reviewCount: Int?=null,
    @SerializedName("deposit") var deposit: Int?=null,
    @SerializedName("dayPrice") var dayPrice: Int?=null,
    @SerializedName("hourPrice") var hourPrice: Int?=null,
    @SerializedName("isLiked") var isLiked: Boolean?,
    @SerializedName("content") var content: String?=null,
    @SerializedName("userNickname") var userNickname: String?=null,
    @SerializedName("userImage") var userImage: String?=null
)