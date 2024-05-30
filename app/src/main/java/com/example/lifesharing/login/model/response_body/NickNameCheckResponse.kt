package com.example.lifesharing.login.model.response_body

import com.google.gson.annotations.SerializedName

data class NickNameCheckResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("code") var code: String?,
    @SerializedName("message") var message: String,
    @SerializedName("result") var result: CheckResult
)

data class CheckResult(
    @SerializedName("message") val message: String,
    @SerializedName("existNickname") var existNickname: Boolean?,
)
