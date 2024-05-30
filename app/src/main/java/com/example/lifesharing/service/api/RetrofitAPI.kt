package com.example.lifesharing.service.api

import com.example.lifesharing.service.RetrofitInterface.RetrofitService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.lifesharing.BuildConfig.*

/**
 * 네트워크 통신을 구현하기 위해 Retrofit 라이브러리를 설정하고 관리
 * RetrofitAPI 객체를 싱글톤으로 선언. 이 객체는 앱 전체에서 하나의 인스턴스만을 유지하게 됨.
 */
object RetrofitAPI {

    // API의 기본 URL
    private const val BASE_URL = BASE_URLS

    // OkHttpClient는 HTTP 클라이언트로 네트워크 요청을 관리
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    // Retrofit 객체를 지연 초기화. Retrofit은 REST API를 자바 인터페이스로 변환하는 데 사용
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)                                    // Retrofit 설정을 위한 기본 URL을 지정
            .addConverterFactory(GsonConverterFactory.create())   // JSON 응답을 자동으로 객체로 변환하기 위해 GsonConverterFactory를 사용
            .client(okHttpClient)                                 // 네트워크 요청을 처리 -> logcat에서 패킷 내용을 모니터링 (interceptor)
            .build()
    }

    // RetrofitService 인터페이스의 인스턴스를 생성. 이 인터페이스를 통해 API 메소드를 호출.
    val emgMedService: RetrofitService by lazy {
        retrofit.create(RetrofitService::class.java)
    }

}