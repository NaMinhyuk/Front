package com.example.lifesharing

import android.app.Application
import android.content.Context
import com.google.firebase.BuildConfig
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // kakao sdk init
        NaverIdLoginSDK.initialize(this, "3SvlouKq1mjS2CR8T2nX", "3R_PtZHz5X", "lifesharing")

        KakaoSdk.init(this, com.example.lifesharing.BuildConfig.KAKAO_NATIVE_APP_KEY)
    }

}