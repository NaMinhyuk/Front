package com.example.lifesharing.product.api

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class AddHeartResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("code") var code: String?,
    @SerializedName("message") var message: String,
    @SerializedName("result") var result: addHeartResult?
)

data class addHeartResult(
    @SerializedName("createdAt") val createdAt: LocalDateTime?
)