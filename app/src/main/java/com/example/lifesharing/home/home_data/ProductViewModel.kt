package com.example.lifesharing.home.home_data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.service.RetrofitInterface.RetrofitService
import com.example.lifesharing.service.api.RetrofitAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG: String = "ProductViewModel"
    val filteredProducts: MutableLiveData<List<Product>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    private val retrofitService: RetrofitService = RetrofitAPI.retrofit.create(RetrofitService::class.java)


    fun filterProductsBy(filter: String) {
        isLoading.value = true

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = retrofitService.getFilteredProducts(filter)
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    productResponse?.let {
                        filteredProducts.postValue(it.result.productResultDTOList)
                    }
                } else {
                    errorMessage.postValue("API 호출 실패: ${response.code()}")
                    Log.e(TAG, "API 호출 실패: ${response.code()}")
                }
            } catch (e: Exception) {
                errorMessage.postValue("네트워크 오류: ${e.message}")
                Log.e(TAG, "네트워크 오류: ${e.message}", e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }
}