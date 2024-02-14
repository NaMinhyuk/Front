package com.example.lifesharing.service.work

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.product.model.response_body.DetailResult
import com.example.lifesharing.product.model.response_body.DetailReviewList
import com.example.lifesharing.product.model.response_body.Detail_ResponseBody
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class DetailProduct() {
    private val retrofitService = RetrofitAPIwithToken.retrofit()
    val DetailProductList : MutableLiveData<List<DetailResult>> = MutableLiveData()
    val DetailReviewList : MutableLiveData<List<DetailReviewList>> =MutableLiveData()
    val TAG : String = "로그"

    fun detailProduct() {
        //productId 부분을 인텐트로 받아와야 할거같음 현재 등록되어있는 값만 준 상태
        retrofitService.getProductDetails(productId = 10044).enqueue(object : retrofit2.Callback<Detail_ResponseBody> {
            override fun onResponse(call: Call<Detail_ResponseBody>, response: Response<Detail_ResponseBody>) {
                val result = response.body()
                if(response.isSuccessful) {
                    Log.d(TAG, "상품 정보 를 받아왔나요? $result")
                }else {
                    Log.e(TAG, "통신실패 원인은 ${response.message()}", )
                }
            }

            override fun onFailure(call: Call<Detail_ResponseBody>, t: Throwable) {
                Log.e(TAG, "그냥 실패 원인은 ${t.message}", )
            }

        })
    }
}