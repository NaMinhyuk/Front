package com.example.lifesharing.product.work

import android.util.Log
import com.example.lifesharing.product.api.ProductReviewResponse
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

/** 제품 리뷰 조회 Model */
class ProductReviewWork {
    private val service = RetrofitAPIwithToken.retrofit()
    val TAG : String = "제품 리뷰 로그"

    fun getProductReview(productId : Long, callback: (ProductReviewResponse?, String?) -> Unit){
        service.getProductReview(productId).enqueue(object : retrofit2.Callback<ProductReviewResponse>{
            override fun onResponse(
                call: Call<ProductReviewResponse>,
                response: Response<ProductReviewResponse>
            ) {
                // 리뷰 목록을 성공적으로 받아온 경우
                if (response.isSuccessful){
                    callback(response.body(), null)
                    Log.d(TAG, "리뷰 데이터 조회 성공: ${response.body()}")
                }
                else{
                    // 서버로부터 응답은 받았으나, 요청이 실패한 경우 (예: 404, 500 에러 등)
                    callback(null, "리뷰 데이터 조회 실패: ${response.code()}: ${response.message()}")
                    Log.e(TAG, "리뷰 데이터 조회 실패: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ProductReviewResponse>, t: Throwable) {
                // 네트워크 문제나 서버 연결 실패 등의 이유로 요청 자체가 실패한 경우
                callback(null, t.message ?: "Unknown error")
                Log.e(TAG, "네트워크 문제 또는 서버 연결 실패", t)
            }

        })
    }
}