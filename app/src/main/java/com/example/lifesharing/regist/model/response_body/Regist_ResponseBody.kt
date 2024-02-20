package com.example.lifesharing.regist.model.response_body

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ProductRegisterResponseBody(
    @SerializedName("isSuccess") var isSuccess: Boolean?,
    @SerializedName("code") var code: String?,
    @SerializedName("message") var message: String,
    @SerializedName("result") var result: RegistResult?= RegistResult(),
)

data class RegistResult(
    @SerializedName("productId")  var productId: Int?=null,
    @SerializedName("createdAt")  var createdAt: String?=null
)
