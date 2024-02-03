package com.example.lifesharing.common.response_body

import com.example.lifesharing.messenger.model.response_body.MessengerRoomListTempResult
import com.google.gson.annotations.SerializedName

data class ProductIdResponseBody(
    @SerializedName("isSuccess") var isSuccess: Boolean?,
    @SerializedName("code") var code: String?=null,
    @SerializedName("message") var message: String?=null,
    @SerializedName("result") var result: Product?
)

data class Product(
    @SerializedName("productId") var productId: Int?=null,
    @SerializedName("categoryId") var categoryId: Int?=null,
    @SerializedName("userId") var userId: Int?=null,
    @SerializedName("categoryName") var categoryName: String?=null,
    @SerializedName("location") var location: String?=null,
    @SerializedName("detailAddress") var detailAddress: String?=null,
    @SerializedName("imageUrl") var imageUrl: ArrayList<String>?=null,
    @SerializedName("name") var name: String?=null,
    @SerializedName("score") var score: Int?=null,
    @SerializedName("reviewCount") var reviewCouunt: Int?=null,
    @SerializedName("deposit") var deposit: Int?=null,
    @SerializedName("dayPrice") var dayPrice: Int?=null,
    @SerializedName("hourPrice") var hourPrice: Int?=null,
    @SerializedName("isLiked") var isLiked: Boolean?,
    @SerializedName("content") var content: String?=null,
    @SerializedName("userNickname") var userNickname: String?=null,
    @SerializedName("userImage") var userImage: String?=null,
    @SerializedName("reviewList") var reviewList: ArrayList<Review>?=null
)

data class Review(
    @SerializedName("reviewId") var reviewId: Int?=null,
    @SerializedName("userId") var userId: Int?=null,
    @SerializedName("nickName") var nickName: String?=null,
    @SerializedName("profileUrl") var profileUrl: String?=null,
    @SerializedName("createdAt") var createdAt: String?=null,
    @SerializedName("lentDay") var lentDay: String?=null,
    @SerializedName("imageList") var imageList: ArrayList<String>?=null,
)