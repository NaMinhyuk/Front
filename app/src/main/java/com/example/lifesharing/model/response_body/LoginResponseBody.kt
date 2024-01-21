package com.example.lifesharing.model.response_body

import com.google.gson.annotations.SerializedName

data class LoginResponseBody(
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("code") var code: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("result")
    var result: LoginResult? = LoginResult()
)

data class LoginResult(
    @SerializedName("userId") var userId: Int? = null,
    @SerializedName("token") var token: String? = null,
    @SerializedName("createdAt") var createdAt: String? = null
)
