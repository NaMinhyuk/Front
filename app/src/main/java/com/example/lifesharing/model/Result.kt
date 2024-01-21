package com.example.lifesharing.model

import com.example.lifesharing.model.response_body.LoginResponseBody

sealed class Result {
    data class Success(val result: LoginResponseBody?) : Result()

    data class Error(val eerorMessage: String) : Result()
}