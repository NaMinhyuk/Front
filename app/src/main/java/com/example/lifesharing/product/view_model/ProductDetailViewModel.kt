package com.example.lifesharing.product.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.common.response_body.ProductIdResponseBody
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** 제품 상세 정보 요청 API */
class ProductDetailViewModel(application: Application) : AndroidViewModel(application) {
    // Retrofit을 사용하여 HTTP 요청 수행
    private val service = RetrofitAPIwithToken.retrofit()

    val productData = MutableLiveData<ProductIdResponseBody>()     // 제품 상세 정보를 담을 LiveData

    val TAG : String = "ProductDetailViewModel"

    // 제품 아이디를 인자로 넘겨 받아 해당 제품의 상세 정보를 얻음
    fun loadProductData(productId: Long) {
        service.getProductData(productId).enqueue(object : Callback<ProductIdResponseBody> {
            override fun onResponse(call: Call<ProductIdResponseBody>, response: Response<ProductIdResponseBody>) {
                if (response.isSuccessful) {
                    productData.value = response.body()
                    Log.d(TAG, "제품 상세 정보 가져오기 성공 : ${response.body()?.result}")

                } else {

                    Log.e(TAG, "Error fetching product data")
                }
            }

            override fun onFailure(call: Call<ProductIdResponseBody>, t: Throwable) {
                Log.e(TAG, "Network error when fetching product data", t)
            }
        })
    }
}