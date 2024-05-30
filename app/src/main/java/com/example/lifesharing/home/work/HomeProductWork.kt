package com.example.lifesharing.home.work

import android.util.Log
import com.example.lifesharing.home.home_data.ProductResponse
import com.example.lifesharing.home.home_data.ProductResultDTO
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/** 홈 제품 조회 요청 API */
class HomeProductWork {
    private val TAG = "로그"
    // Retrofit을 사용하여 HTTP 요청 수행
    private val service = RetrofitAPIwithToken.retrofit()

    fun getFilteredProducts(filter: String, callback: (List<ProductResultDTO>?, String?) -> Unit) {
        service.getFilteredProducts(filter).enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()?.ProductResultDTOList?.productResultDTO
                    // 요청이 완료되면 콜백 함수 호출
                    callback(result, null)
                    Log.d(TAG, "상품 필터 정보 $result")   // response body 로그로 확인
                } else {
                    callback(null, "Failed to load filtered products: ${response.message()}")
                    Log.e(TAG, " 상품 정보 가져오기 실패: ${response.message()}" )
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                callback(null, "Network failure: ${t.message}")
                Log.e(TAG, "통신 실패: ${t.message}")
            }
        })
    }
}