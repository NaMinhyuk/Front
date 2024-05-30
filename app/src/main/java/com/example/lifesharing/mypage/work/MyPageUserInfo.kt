package com.example.lifesharing.mypage.work

import android.util.Log
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.mypage.mypage_data.UserInfoResponse
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

/** 마이페이지 정보 조회 API */
class MyPageUserInfo {

    val TAG = "로그"

    // Retrofit을 사용하여 HTTP 요청 수행음
    private val service = RetrofitAPIwithToken.retrofit()

    fun getMyPageUserInfo() {
        service.getUserProfile()
            .enqueue(object : retrofit2.Callback<UserInfoResponse> {
                override fun onResponse(
                    call: Call<UserInfoResponse>,
                    response: Response<UserInfoResponse>
                ) {
                    val result = response.body()

                    try {
                        GlobalApplication.setUserInfoData(result!!.UserInfoResultDTO)    // GlobalApplication에 사용자의 정보 설정
                        Log.d(TAG, "getUserInfo $result")
                    } catch(e: Exception) {
                        Log.e(TAG, " ${e.message} ", )
                    }


                }

                override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}", )
                }

            })
    }

}
