package com.example.lifesharing.product.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.product.api.ProductEditInfoResponse
import com.example.lifesharing.product.api.ProductInfoResult
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class ProductInfoViewModel(application: Application): AndroidViewModel(application) {
    private val service = RetrofitAPIwithToken.retrofit()
    val TAG : String = "제품 수정 정보 요청 로그"

    val productData : MutableLiveData<ProductInfoResult> = MutableLiveData()

    fun getProductEditInfo(productId : Long){
        service.getProductEditInfo(productId).enqueue(object : retrofit2.Callback<ProductEditInfoResponse>{
            override fun onResponse(
                call: Call<ProductEditInfoResponse>,
                response: Response<ProductEditInfoResponse>
            ) {
                if (response.isSuccessful){
                    val result = response.body()
                    productData.postValue(response.body()?.result!!)

                    Log.d(TAG, "제품 수정 정보 요청 성공 : ${result}")
                }
                else{
                    Log.e(TAG, "제품 수정 정보 요청 실패 : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProductEditInfoResponse>, t: Throwable) {
                Log.e(TAG, "네트워크 오류 : ${t.message}")
            }

        })
    }
}