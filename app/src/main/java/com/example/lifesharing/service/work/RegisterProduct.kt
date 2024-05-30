package com.example.lifesharing.service.work

import android.util.Log
import com.example.lifesharing.regist.model.request_body.ProductRegisterRequestBody
import com.example.lifesharing.regist.model.response_body.ProductRegisterResponseBody
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

/** 제품 등록 Model */
class RegisterProduct(private val imageList : ArrayList<MultipartBody.Part>, private val productData: MultipartBody.Part ) {

    private val retrofitService = RetrofitAPIwithToken.retrofit()

    val TAG: String = "로그"

    fun registerProduct() {
        retrofitService.registerProduct(imageList,productData )
            .enqueue(object : retrofit2.Callback<ProductRegisterResponseBody> {
                override fun onResponse(
                    call: Call<ProductRegisterResponseBody>,
                    response: Response<ProductRegisterResponseBody>
                ) {

                    if (response.isSuccessful) {
                        // 서버로부터 성공적인 응답을 받음
                        val result = response.body()
                        Log.d(TAG, "onResponse: $result")
                    } else {
                        // 서버로부터 에러 응답을 받음 (예: 4xx or 5xx HTTP Status)
                        Log.d(TAG, "onResponse - not successful: ${response.errorBody()?.string()}")
                        Log.d(TAG, "HTTP Status Code: ${response.code()}")
                    }
                    Log.d(TAG, "ArrayList: $imageList")
                }

                override fun onFailure(call: Call<ProductRegisterResponseBody>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
    }
}