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
import com.example.lifesharing.mypage.mypage_api.InquiryDTO
import com.example.lifesharing.mypage.mypage_api.UserInfoResultDTO
import com.example.lifesharing.mypage.review.model.response_body.ReviewListDTOList
import com.kakao.sdk.user.model.User
import com.example.lifesharing.product.model.response_body.ProductMenuResultDTO

class GlobalApplication : Application() {

    companion object {

        lateinit var instance: GlobalApplication
            private set

        private var productData: Product? = null

        private var data: ArrayList<MessengerRoomListTempResult>? = null

        private var myReviewCount: Int? = null

        private var myReviewData: ArrayList<ReviewListDTOList>? = null

        private var userInfoData: UserInfoResultDTO? = null

        lateinit var prefs: PreferenceUtil
        private var menuDetailProductDataList: ArrayList<ProductMenuResultDTO>? = null

        fun getMenuDetailProductDataList(): ArrayList<ProductMenuResultDTO> {
            return menuDetailProductDataList!!
        }

        fun setMenuDetailProductDataList(menuData: ArrayList<ProductMenuResultDTO>) {
            menuDetailProductDataList = menuData
        }

        fun getMyReviewCount(): Int? {
            return myReviewCount
        }

        fun setMyReviewCount(reviewCount: Int) {
            myReviewCount = reviewCount
        }

        fun getMyReviewData(): ArrayList<ReviewListDTOList>? {
            return myReviewData
        }

        fun setMyReviewData(reviewData: ArrayList<ReviewListDTOList>) {
            myReviewData = reviewData
        }

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


        private var qnaListData: ArrayList<InquiryDTO>?=null

        fun getQnaListData(): ArrayList<InquiryDTO> {
            return qnaListData!!
        }

        fun setQnaListData(data : ArrayList<InquiryDTO>) {
            qnaListData = data
        }


        fun getUserInfoData(): UserInfoResultDTO {
            return userInfoData!!
        }

        fun setUserInfoData(userData: UserInfoResultDTO) {
            userInfoData = userData
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