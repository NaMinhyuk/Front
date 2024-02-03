package com.example.lifesharing.service.work

import android.util.Log
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.common.response_body.ProductIdResponseBody
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class ProductWork(private val productId: Int) {

    private val service = RetrofitAPIwithToken.retrofit()

    val TAG: String = "로그"

    fun getProductInfo() {
        service.getProductData(productId)
            .enqueue(object : retrofit2.Callback<ProductIdResponseBody> {
                override fun onResponse(
                    call: Call<ProductIdResponseBody>,
                    response: Response<ProductIdResponseBody>
                ) {
                    val result = response.body()

                    GlobalApplication.setProductData(result?.result!!)

                    Log.d(TAG, "onResponse: ${GlobalApplication.getProductData()}")
                    
                }

                override fun onFailure(call: Call<ProductIdResponseBody>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}", )
                }

            })
    }


}
