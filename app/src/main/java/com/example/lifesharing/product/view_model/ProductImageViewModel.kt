package com.example.lifesharing.product.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.product.api.ProductUpdateResponse
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

class ProductImageViewModel(application: Application) : AndroidViewModel(application) {
    private val service = RetrofitAPIwithToken.retrofit()

    val TAG : String = "제품 이미지 수정 로그"
    val updateImageResult: MutableLiveData<Boolean> = MutableLiveData()   // 제품 이미지 수정 성공 여부 관찰 데이터

    fun updateProductImage(productId : Long, imageUrl : ArrayList<MultipartBody.Part>){
        service.updateProductImage(productId, imageUrl).enqueue(object : retrofit2.Callback<ProductUpdateResponse>{
            override fun onResponse(
                call: Call<ProductUpdateResponse>,
                response: Response<ProductUpdateResponse>
            ) {
                if (response.isSuccessful){
                    updateImageResult.postValue(true)
                    Log.d(TAG, "제품 이미지 수정 성공 : ${response.body()}")
                }
                else {
                    updateImageResult.postValue(false)
                    Log.e(TAG, "제품 이미지 수정 실패 : ${response}")
                }
            }

            override fun onFailure(call: Call<ProductUpdateResponse>, t: Throwable) {
                updateImageResult.postValue(false)
                Log.e(TAG, "네트워크 통신 오류 : ${t.message}")
            }

        })
    }
}