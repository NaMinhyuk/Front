package com.example.lifesharing.login.model.response_body

import com.google.gson.annotations.SerializedName

data class NickNameChangeResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("code") var code: String?,
    @SerializedName("message") var message: String,
    @SerializedName("result") var result: ChangeResult
)

data class ChangeResult(
    @SerializedName("userId") var userId: Long,
    @SerializedName("nickname") var nickname: String,
    @SerializedName("updatedAt") var updatedAt: String
)