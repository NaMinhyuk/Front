package com.example.lifesharing

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.example.lifesharing.BuildConfig.*
import com.example.lifesharing.common.response_body.Product
import com.example.lifesharing.messenger.model.response_body.MessengerRoomListTempResult
import com.example.lifesharing.mypage.mypage_data.UserInfoResultDTO
import com.example.lifesharing.mypage.review.model.response_body.ReviewListDTOList
import com.example.lifesharing.service.work.CustomTokenManager
import com.example.lifesharing.util.PreferenceUtil
import com.jakewharton.threetenabp.AndroidThreeTen
import com.kakao.sdk.auth.TokenManagerProvider


/** Android Application에서 전역적으로 사용
 * 앱의 생명주기를 제어하고, 앱 전역에서 사용할 수 있는 데이터와 유틸리티를 저장하는 역할 */
class GlobalApplication : Application() {


    // 여러 종류의 데이터(제품 데이터, 채팅방 목록, 사용자 리뷰, 사용자 정보 등)와 유틸리티(설정) 객체를 저장하는 정적변수와 메서드 정의
    companion object {

        private var productData: Product? = null

        private var data: ArrayList<MessengerRoomListTempResult>? = null

        private var myReviewCount: Int? = null

        private var myReviewData: ArrayList<ReviewListDTOList>? = null

        private var userInfoData: UserInfoResultDTO? = null

        lateinit var prefs: PreferenceUtil
        lateinit var instance: GlobalApplication
            private set

        // 사용자 리뷰 데이터 개수 조회
        fun getMyReviewCount() : Int? {
            return myReviewCount
        }

        // 사용자 리뷰 데이터 개수 설정
        fun setMyReviewCount(reviewCount : Int) {
            myReviewCount = reviewCount
        }

        // 사용자 리뷰 데이터 조회
        fun getMyReviewData() : ArrayList<ReviewListDTOList>? {
            return myReviewData
        }

        // 사용자 리뷰 데이터 설정
        fun setMyReviewData(reviewData : ArrayList<ReviewListDTOList>) {
            myReviewData = reviewData
        }

        // 사용자 채팅방 리스트 조회
        fun getData(): ArrayList<MessengerRoomListTempResult>? {
            return data
        }

        // 사용자 채팅방 리스트 설정
        fun setData(_data: ArrayList<MessengerRoomListTempResult>?) {
            data = _data
        }

        // 제품 조회
        fun getProductData(): Product? {
            return productData
        }

        // 제품 설정
        fun setProductData(_productData: Product) {
            productData = _productData
        }

        // 사용자 정보 조회
        fun getUserInfoData(): UserInfoResultDTO {
            return userInfoData!!
        }

        // 사용자 정보 설정
        fun setUserInfoData(userData: UserInfoResultDTO) {
            userInfoData = userData
        }
    }

    // Application 초기화 로직 행
    override fun onCreate() {
        super.onCreate()

        prefs = PreferenceUtil(applicationContext)

        instance = this

        // Naver SDK init
        NaverIdLoginSDK.initialize(this, NAVER_CLIENT_ID, NAVER_SECRET_KEY, "lifesharing")

        // Kakao SDK init
        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)

        initializeCustomTokenManager()

        // 날짜 및 시간 라이브러리 초기화
        AndroidThreeTen.init(this)
    }


    // 카카오 로그인에 사용될 사용자 정의 토큰 관리자를 설정
    private fun initializeCustomTokenManager() {
        TokenManagerProvider.instance.manager = CustomTokenManager()
    }
}