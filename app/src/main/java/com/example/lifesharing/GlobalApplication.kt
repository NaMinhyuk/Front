package com.example.lifesharing

import android.app.Application
import android.content.Context
import com.example.lifesharing.sharedPref.PreferenceUtil
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.example.lifesharing.BuildConfig.*
import com.example.lifesharing.product.model.response_body.ProductMenuResultDTO

class GlobalApplication : Application() {

    companion object {
        lateinit var prefs: PreferenceUtil
        private var menuDetailProductDataList :ArrayList<ProductMenuResultDTO>?= null

        fun getMenuDetailProductDataList() : ArrayList<ProductMenuResultDTO> {
            return menuDetailProductDataList!!
        }

        fun setMenuDetailProductDataList (menuData: ArrayList<ProductMenuResultDTO>) {
            menuDetailProductDataList = menuData
        }
    }

    override fun onCreate() {
        super.onCreate()
        // kakao sdk init

        prefs = PreferenceUtil(applicationContext)

        NaverIdLoginSDK.initialize(this, NAVER_CLIENT_ID, NAVER_SECRET_KEY, "lifesharing")

        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)
    }



}