package com.example.lifesharing

import android.Manifest
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.lifesharing.util.PreferenceUtil
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.example.lifesharing.BuildConfig.*
import com.example.lifesharing.util.RequestPermissionsUtil

class GlobalApplication : Application() {


    companion object {
        lateinit var prefs: PreferenceUtil

        lateinit var permissionLoc : RequestPermissionsUtil

        lateinit var instance: GlobalApplication
            private set
    }



    override fun onCreate() {
        super.onCreate()
        // kakao sdk init

        prefs = PreferenceUtil(applicationContext)

        instance = this

        NaverIdLoginSDK.initialize(this, NAVER_CLIENT_ID, NAVER_SECRET_KEY, "lifesharing")

        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)
    }

}