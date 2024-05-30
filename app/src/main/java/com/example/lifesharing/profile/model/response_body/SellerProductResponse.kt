package com.example.lifesharing.profile.model.response_body

import com.google.gson.annotations.SerializedName

data class SellerProductResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("code") var code: String?,
    @SerializedName("message") var message: String,
    @SerializedName("result") var result: SellerProductResult?
)

data class SellerProductResult (
    @SerializedName("productList") var productList: List<SellerProductList>?=null
)

data class SellerProductList (
    @SerializedName("productId") var productId: Long?,
    @SerializedName("name") var name: String?,
    @SerializedName("location") var location: String?,
    @SerializedName("score") var score: Int?,
    @SerializedName("reviewCount") var reviewCount: Int?,
    @SerializedName("deposit") var deposit: Int?,
    @SerializedName("dayPrice") var dayPrice: Int?,
    @SerializedName("imageUrl") var imageUrl: String?,
    @SerializedName("isReserved") var isReserved: String?
)
