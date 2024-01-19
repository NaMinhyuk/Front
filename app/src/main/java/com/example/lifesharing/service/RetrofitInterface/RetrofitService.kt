package com.example.lifesharing.service.RetrofitInterface

import com.example.lifesharing.model.request_body.LoginRequestBody
import com.example.lifesharing.model.request_body.RegisterRequestBody
import com.example.lifesharing.model.response_body.LoginResponseBody
import com.example.lifesharing.model.response_body.RegisterResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitService {

    @Headers("Content-Type: application/json")
    @POST("user/join")
    fun registerUserByEnqueue(@Body userInfo: RegisterRequestBody): Call<RegisterResponseBody> // call은 흐름처리 기능 제공

    @Headers("Content-Type: application/json")
    @POST("user/login")
    fun loginUserByEnqueue(@Body userInfo: LoginRequestBody): Call<LoginResponseBody>

}