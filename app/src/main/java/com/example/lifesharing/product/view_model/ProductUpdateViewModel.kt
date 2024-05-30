package com.example.lifesharing.product.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.product.api.ProductUpdateResponse
import com.example.lifesharing.product.data.UpdateRequestData
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class ProductUpdateViewModel(application: Application) : AndroidViewModel(application) {
    private val service = RetrofitAPIwithToken.retrofit()

    val TAG : String = "제품 정보 수정 로그"
    val updateResult: MutableLiveData<Boolean> = MutableLiveData()   // 제품 수정 성공 여부 관찰 데이터

    fun updateProductInfo(productId : Long, productInfo : UpdateRequestData){
        service.updateProductInfo(productId, productInfo).enqueue(object : retrofit2.Callback<ProductUpdateResponse>{
            override fun onResponse(
                call: Call<ProductUpdateResponse>,
                response: Response<ProductUpdateResponse>
            ) {
                if(response.isSuccessful){    // 제품 정보 수정 요청 성공시
                    updateResult.postValue(true)
                    Log.d(TAG, "제품 정보 수정 성공 : ${response.body()}")
                }
                else{
                    updateResult.postValue(false)
                    Log.e(TAG, "제품 정보 수정 실패 : ${response.body()}")
                }
            }

            override fun onFailure(call: Call<ProductUpdateResponse>, t: Throwable) {
                updateResult.postValue(false)
                Log.e(TAG, "네트워크 에러 : ${t.message}")
            }

        })
    }
}