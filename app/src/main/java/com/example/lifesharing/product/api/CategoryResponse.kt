package com.example.lifesharing.product.api

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("code") var code: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("result") var result: CategoryResult?
)

data class CategoryResult(
    @SerializedName("productResultDTOList") var productList: ArrayList<CategoryItem>
)

data class CategoryItem(
    @SerializedName("productId") var productId: Long?,
    @SerializedName("name") var name: String? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("deposit") var deposit: Int?,
    @SerializedName("dayPrice") var dayPrice: Int?,
    @SerializedName("score") var score: Int?,
    @SerializedName("reviewCount") var reviewCount: Int?,
    @SerializedName("image_url") var imageUrl: String?=null,
)