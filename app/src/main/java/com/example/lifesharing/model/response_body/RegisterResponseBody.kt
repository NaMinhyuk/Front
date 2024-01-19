package com.example.lifesharing.model.response_body

import com.google.gson.annotations.SerializedName

data class RegisterResponseBody(
    @SerializedName("isSuccess")
    val isSuccess: Boolean?,
    @SerializedName("result")
    val result: Map<String, String>?
)
