package com.example.lifesharing.login.model.response_body

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
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("tokenDTO") var tokenDTO: TokenDTO? = TokenDTO()
)

data class TokenDTO(
    @SerializedName("grantType") var grantType: String? = null,
    @SerializedName("accessToken") var accessToken: String? = null,
    @SerializedName("refreshToken") var refreshToken: String? = null,
)