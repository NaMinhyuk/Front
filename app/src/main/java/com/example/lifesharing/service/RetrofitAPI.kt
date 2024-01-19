package com.example.lifesharing.service

import com.example.lifesharing.service.RetrofitInterface.RetrofitService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAPI {

    private const val BASE_URL = "https://dev.lifesharing.shop/"

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient) // logcat에서 패킷 내용을 모니터링 할 수 있다 (interceptor)
            .build()
    }

    val emgMedService: RetrofitService by lazy {
        retrofit.create(RetrofitService::class.java)
    }

}