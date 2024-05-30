package com.example.lifesharing.profile.work

import android.util.Log
import com.example.lifesharing.profile.model.response_body.SellerProductResponse
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

/** 대여자 프로필 - 대여중 물품 조회 Model */
class SellerRentProductWork {
    private val retrofitService = RetrofitAPIwithToken.retrofit()
    val TAG: String = "대여자 대여중 물품 로그"

    // 대여자가 등록한 대여중인 물품 조회
    fun getSellerRentProduct(sellerId : Long, callback: (SellerProductResponse?, String?) -> Unit){
        retrofitService.getSellerRentProducts(sellerId).enqueue(object : retrofit2.Callback<SellerProductResponse>{
            override fun onResponse(
                call: Call<SellerProductResponse>,
                response: Response<SellerProductResponse>
            ) {
                if (response.isSuccessful){
                    val result = response.body()?.result?.productList

                    //itemResult.postValue(result)
                    callback(response.body(), null)    // 성공적으로 데이터를 받아온 경우 콜백을 통해 결과 전달
                    Log.d(TAG, "대여자 대여중 제품 조회 성공: $result")
                }
                else {
                    callback(null, "대여자 대여중 제품 조회 실패: ${response.body()}")
                    Log.e(TAG, "대여자 대여중 제품 조회 실패: $response")
                }
            }

            override fun onFailure(call: Call<SellerProductResponse>, t: Throwable) {
                callback(null, "대여자 대여중 제품 조회 실패: ${t.message}")
                Log.e(TAG, "네트워크 통신 오류 : ${t.message}")
            }

        })
    }
}