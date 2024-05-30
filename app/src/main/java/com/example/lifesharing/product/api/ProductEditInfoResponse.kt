package com.example.lifesharing.product.api

import com.google.gson.annotations.SerializedName

data class ProductEditInfoResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("code") var code: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("result") var result: ProductInfoResult?
)

data class ProductInfoResult(
    @SerializedName("categoryId") var categoryId: String,
    @SerializedName("name") var name: String,
    @SerializedName("content") var content: String,
    @SerializedName("dayPrice") var dayPrice: Int,
    @SerializedName("hourPrice") var houPrice: Int,
    @SerializedName("deposit") var deposit: Int,
    @SerializedName("lendingPeriod") var lendingPeriod: String
)
