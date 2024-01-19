package com.example.lifesharing

import android.app.Application
import com.google.firebase.BuildConfig
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // kakao sdk init
        KakaoSdk.init(this, com.example.lifesharing.BuildConfig.KAKAO_NATIVE_APP_KEY)
    }

}