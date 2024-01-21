package com.example.lifesharing.model.response_body

import com.google.gson.annotations.SerializedName

data class RegisterResponseBody(
    @SerializedName("isSuccess") var isSuccess: Boolean? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("result") var result: RegisterResult? = RegisterResult()
)

data class RegisterResult(
    @SerializedName("userId") var userId: Int? = null,
    @SerializedName("token") var token: String? = null,
    @SerializedName("createdAt") var createdAt: String? = null
)
