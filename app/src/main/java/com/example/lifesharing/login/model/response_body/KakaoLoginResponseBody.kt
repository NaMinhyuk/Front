package com.example.lifesharing.login.model.response_body

import com.example.lifesharing.mypage.review.model.response_body.ReviewResult
import com.google.gson.annotations.SerializedName

data class KakaoLoginResponseBody(
    @SerializedName("isSuccess") var isSuccess: Boolean?,
    @SerializedName("code") var code: String?=null,
    @SerializedName("message") var message: String?=null,
    @SerializedName("result") var result: TokenDTO
)
