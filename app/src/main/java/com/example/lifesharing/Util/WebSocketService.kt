package com.example.lifesharing.util

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.lifesharing.BuildConfig.BASE_URLS
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit

class WebSocketService: Service() {

    val TAG: String = "로그"

    val BASE_URL = "wss://${BASE_URLS}"

    private var webSocket: WebSocket? = null

    override fun onCreate() {
        super.onCreate()
        var okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .connectTimeout(15000, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(true)
            .build()

        var request: Request = Request.Builder().url(BASE_URL).build()
        //var webSocket: WebSocketCall = WebSocketCall.create(okHttpClient, request)
    }
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}

