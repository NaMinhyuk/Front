package com.example.lifesharing.product.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.product.api.ProductDeleteResponse
import com.example.lifesharing.product.work.ProductDeleteWork

class DeleteViewModel(application: Application, private val productId: Long): AndroidViewModel(application) {

    private val deleteWork = ProductDeleteWork()
    val deleteStatus = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun deleteProduct() {
        deleteWork.productDelete(productId) { isSuccess, error ->
            deleteStatus.postValue(isSuccess)
            errorMessage.postValue(error)
        }
    }
}