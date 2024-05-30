package com.example.lifesharing.mypage.model.response_body

import com.google.gson.annotations.SerializedName

data class ChangePwResponse (
    @SerializedName("isSuccess") var isSuccess: Boolean?,
    @SerializedName("code") var code: String?=null,
    @SerializedName("message") var message: String?=null,
    @SerializedName("result") var result: ChangeResult?
)

data class ChangeResult(
    @SerializedName("isChanged") var isChanged: Boolean?,
    @SerializedName("updatedAt") var updatedAt: String
)
