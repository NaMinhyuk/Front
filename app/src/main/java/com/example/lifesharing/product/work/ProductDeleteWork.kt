package com.example.lifesharing.product.work

import android.util.Log
import com.example.lifesharing.product.api.ProductDeleteResponse
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class ProductDeleteWork {
    private val service = RetrofitAPIwithToken.retrofit()
    private val TAG: String = "로그"

    fun productDelete(productId: Long, callback: (Boolean, String?) -> Unit){
        Log.d(TAG, "getProductId:$productId")

        service.productDelete(productId).enqueue(object : retrofit2.Callback<ProductDeleteResponse>{
            override fun onResponse(
                call: Call<ProductDeleteResponse>,
                response: Response<ProductDeleteResponse>
            ) {
                if (response.isSuccessful){
                    // 성공적으로 처리됨
                    Log.d(TAG, "Product deletion successful: ${response.body()?.message}")
                    callback(true, null)
                }
                else
                {
                    // 서버에서 오류 응답
                    Log.e(TAG, "Server response error: ${response.errorBody()?.string()}")
                    callback(false, "제품 삭제 실패: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ProductDeleteResponse>, t: Throwable) {
                // 네트워크 오류 등의 문제
                Log.e(TAG, "Network or server error: ${t.message}")
                callback(false, "Network or server error: ${t.message}")
            }
        })
    }
}