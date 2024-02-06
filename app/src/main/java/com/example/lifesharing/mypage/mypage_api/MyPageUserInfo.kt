package com.example.lifesharing.mypage.mypage_api

import android.util.Log
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.mypage.mypage_api.UserInfoResponse
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class MyPageUserInfo {

    val TAG = "로그"

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
                        GlobalApplication.setUserInfoData(result!!.UserInfoResultDTO)
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