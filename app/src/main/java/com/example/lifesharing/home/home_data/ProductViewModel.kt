package com.example.lifesharing.home.home_data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response


class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val service = RetrofitAPIwithToken.retrofit()

    private val TAG: String = "로그"
    val filteredProducts: MutableLiveData<List<ProductResultDTO>> = MutableLiveData()

    fun getFilteredProduct(filter: String) {
        service.getFilteredProducts(filter).enqueue(object : retrofit2.Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                val result = response.body() // 이 데이터를 뿌려주는 로직 추가
                if (response.isSuccessful) {
                    Log.d(TAG, "상품 필터 정보 $result")

                    Log.d(TAG, " ${result?.productResultDTOList?.productResultDTO} ")

                    filteredProducts.postValue(result?.productResultDTOList?.productResultDTO)

                } else {
                    Log.e(TAG, " 통신 실패: ${response.message()}", )
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e(TAG, "상품정보가져오기 실패: ${t.message}", )
                }
            }
        )
    }
}
