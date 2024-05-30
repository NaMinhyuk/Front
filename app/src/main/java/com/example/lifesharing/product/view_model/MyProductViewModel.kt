package com.example.lifesharing.product.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.R
import com.example.lifesharing.product.data.MyRegisterProductData
import com.example.lifesharing.product.data.myProductResultDTO
import com.example.lifesharing.product.work.MyItemWork
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

/** MY 아이템 조회 ViewModel */
class MyProductViewModel(application: Application) : AndroidViewModel(application){

    val myProducts: MutableLiveData<List<myProductResultDTO>> = MutableLiveData()
    val isLoading = MutableLiveData<Boolean>()

    private val myItemWork = MyItemWork()

    fun getMyItemProduct() {
        isLoading.value = true
        myItemWork.getMyRegisterProduct { products, isSuccess ->
            if (isSuccess) {
                myProducts.postValue(products)
            } else {
                // Error handling, e.g., display an error message
                myProducts.postValue(emptyList())
            }
            isLoading.value = false
        }
    }

}