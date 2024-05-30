package com.example.lifesharing.mypage.model.response_body

import com.google.gson.annotations.SerializedName

data class UsageHistoryResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("code") var code: String?,
    @SerializedName("message") var message: String,
    @SerializedName("result") var result: List<UsageHistoryResult>?,
)

data class UsageHistoryResult(
    @SerializedName("reservationId") var reservationId: Long,
    @SerializedName("productId") var productId: Long,
    @SerializedName("productName") var productName: String,
    @SerializedName("productImage") var productImage: String,
    @SerializedName("location") var location: String,
    @SerializedName("amount") var amount: Long,
    @SerializedName("deposit") var deposit: Long,
    @SerializedName("day") var totalTime: String,
)