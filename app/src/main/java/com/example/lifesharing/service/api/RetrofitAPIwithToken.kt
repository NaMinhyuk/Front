package com.example.lifesharing.service.api

import android.util.Log
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.service.RetrofitInterface.RetrofitService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.example.lifesharing.BuildConfig.*

object RetrofitAPIwithToken {
    private const val BASE_URL = BASE_URLS

    val TAG = "로그"

    @Singleton
    fun okHttpClient(interceptor: AppInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor) // okHttp에 인터셉터 추가
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }
    @Singleton
    fun retrofit(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient(AppInterceptor())) // okHttpClient를 Retrofit 빌더에 추가
            .build()
            .create(RetrofitService::class.java)
    }

    class AppInterceptor : Interceptor {

        val accessToken = GlobalApplication.prefs.getString("access_token", "") // ViewModel에서 지정한 key로 JWT 토큰을 가져온다.
        val token = "Bearer $accessToken"


        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {

            Log.d(TAG, "intercept: $token")

            val newRequest = request().newBuilder()
                .addHeader("Authorization", token) // 헤더에 authorization라는 key로 JWT 를 넣어준다.
                .build()
            proceed(newRequest)
        }
    }
}