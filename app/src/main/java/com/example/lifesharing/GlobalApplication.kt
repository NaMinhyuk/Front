package com.example.lifesharing

import android.Manifest
import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.lifesharing.util.PreferenceUtil
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.example.lifesharing.BuildConfig.*
import com.example.lifesharing.common.response_body.Product
import com.example.lifesharing.common.response_body.ProductIdResponseBody
import com.example.lifesharing.messenger.model.response_body.MessengerRoomListTempResult
import com.example.lifesharing.util.RequestPermissionsUtil

class GlobalApplication : Application() {


    companion object {

        private var productData : Product?=null

        private var data : ArrayList<MessengerRoomListTempResult>? = null

        lateinit var prefs: PreferenceUtil
        //lateinit var permissionLoc : RequestPermissionsUtil 권한 죽이기 위해서
        lateinit var instance: GlobalApplication
            private set

        fun getData(): ArrayList<MessengerRoomListTempResult>? {
            return data
        }

        fun setData(_data: ArrayList<MessengerRoomListTempResult>?) {
            data = _data
        }

        fun getProductData(): Product? {
            return productData
        }

        fun setProductData(_productData: Product) {
            productData = _productData
        }

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