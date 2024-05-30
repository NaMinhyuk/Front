package com.example.lifesharing.product.api

import com.example.lifesharing.login.model.response_body.LoginResult
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ProductDeleteResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("code") var code: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("result") var result: DeleteResult? = DeleteResult()
)

data class DeleteResult(
    @SerializedName("productId") val productId: Long?= null,
    @SerializedName("deletedAt") val deletedAt: String?=null
)
