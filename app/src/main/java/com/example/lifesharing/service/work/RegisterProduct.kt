package com.example.lifesharing.service.work

import android.util.Log
import com.example.lifesharing.regist.model.request_body.ProductRegisterRequestBody
import com.example.lifesharing.regist.model.response_body.ProductRegisterResponseBody
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

class RegisterProduct(private val imageList : ArrayList<MultipartBody.Part>, private val productData: ProductRegisterRequestBody, ) {

    private val retrofitService = RetrofitAPIwithToken.retrofit()

    val TAG: String = "로그"

    fun registerProduct() {
        retrofitService.registerProduct(imageList,productData)
            .enqueue(object : retrofit2.Callback<ProductRegisterResponseBody> {
                override fun onResponse(
                    call: Call<ProductRegisterResponseBody>,
                    response: Response<ProductRegisterResponseBody>
                ) {
                    
                    val result = response.body()
                    Log.d(TAG, "onResponse: $result")
                    Log.d(TAG, "ArrayList: $imageList")
                }

                override fun onFailure(call: Call<ProductRegisterResponseBody>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }

            })
    }


}