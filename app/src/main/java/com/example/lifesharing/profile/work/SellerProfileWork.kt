package com.example.lifesharing.profile.work

import android.util.Log
import com.example.lifesharing.profile.model.response_body.SellerResponseBody
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

/** 대여자 프로필 조회 Model */
class SellerProfileWork {
    private val retrofitService = RetrofitAPIwithToken.retrofit()
    val TAG: String = "대여자 프로필 로그"

    fun getSellerProfile(sellerId: Long, callback: (SellerResponseBody? , String?) -> Unit){
        retrofitService.getSellerProfile(sellerId).enqueue(object : retrofit2.Callback<SellerResponseBody>{
            override fun onResponse(
                call: Call<SellerResponseBody>,
                response: Response<SellerResponseBody>
            ) {
                if (response.isSuccessful) {
                    // 서버로부터 성공적인 응답을 받음
                    val result = response.body()
                    //sellerProfile.postValue(response?.body()?.result)
                    callback(response.body(), null)    // 성공적으로 데이터를 받아온 경우 콜백을 통해 결과 전달

                    Log.d(TAG, "대여자 프로필 불러오기 성공: $result")
                }
                else {
                    callback(null, "대여자 프로플 불러오기 실패 : ${response.body()}")
                    Log.e(TAG, "대여자 프로플 불러오기 실패 : ${response}")
                }
            }

            override fun onFailure(call: Call<SellerResponseBody>, t: Throwable) {
                callback(null, "네트워크 통신 오류 : ${t.message}")
                Log.d(TAG, "네트워크 통신 오류: ${t.message}")
            }

        })
    }
}