package com.example.lifesharing.profile.work

import android.util.Log
import com.example.lifesharing.profile.model.response_body.SellerReviewResponse
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Response

/** 대여자 프로필 - 리뷰 목록 조회 Model */
class SellerReviewWork {
    private val retrofitService = RetrofitAPIwithToken.retrofit()
    val TAG: String = "대여자 리뷰 목록 로그"

    // 대여자의 리뷰 목록 조회
    fun getSellerReview(sellerId : Long, callback: (SellerReviewResponse?, String?) -> Unit){
        retrofitService.getSellerReviews(sellerId).enqueue(object : retrofit2.Callback<SellerReviewResponse>{
            override fun onResponse(
                call: retrofit2.Call<SellerReviewResponse>,
                response: Response<SellerReviewResponse>
            ) {
                if (response.isSuccessful){
                    val result = response.body()?.result?.reviewList

                    //itemResult.postValue(result)
                    callback(response.body(), null)    // 성공적으로 데이터를 받아온 경우 콜백을 통해 결과 전달
                    Log.d(TAG, "대여자 리뷰 목록 조회 성공: $result")
                }
                else{
                    callback(null, "대여자 리뷰 목록 조회 실패: ${response.body()}")
                    Log.e(TAG, "대여자 리뷰 목록 조회 실패: $response")
                }
            }

            override fun onFailure(call: retrofit2.Call<SellerReviewResponse>, t: Throwable) {
                callback(null, "대여자 리뷰 목록 조회 실패: ${t.message}")
                Log.e(TAG, "네트워크 통신 오류 : ${t.message}")
            }

        })
    }
}