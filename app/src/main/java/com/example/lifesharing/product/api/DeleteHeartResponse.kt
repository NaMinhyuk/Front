package com.example.lifesharing.product.api

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class DeleteHeartResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("code") var code: String?,
    @SerializedName("message") var message: String,
    @SerializedName("result") var result: delHeartResult?
)

data class delHeartResult(
    @SerializedName("updatedAt") val createdAt: LocalDateTime?=null
)
