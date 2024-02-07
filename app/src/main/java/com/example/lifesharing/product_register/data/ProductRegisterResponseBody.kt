package com.example.lifesharing.product_register.data

import com.google.gson.annotations.SerializedName

data class ProductRegisterResponseBody(
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("code") var code: String?,
    @SerializedName("message") var message: String,
    @SerializedName("result") var result: RegistResult?= RegistResult(),
)

data class RegistResult(
    @SerializedName("productId")  var productId: Int?=null,
    @SerializedName("createdAt")  var createdAt: String?=null
)
