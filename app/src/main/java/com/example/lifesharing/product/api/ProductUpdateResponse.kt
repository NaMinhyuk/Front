package com.example.lifesharing.product.api

import com.google.gson.annotations.SerializedName

data class ProductUpdateResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("code") var code: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("result") var result: ProductInfoUpdateResult?
)

data class ProductInfoUpdateResult(
    @SerializedName("productId") var productId: Long,
    @SerializedName("updatedAt") var updatedAt: String? = null
)
