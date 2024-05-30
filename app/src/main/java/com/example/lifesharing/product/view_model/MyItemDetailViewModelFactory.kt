package com.example.lifesharing.product.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyItemDetailViewModelFactory(private val application: Application, private val productId: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyItemDetailViewModel::class.java)) {
            return MyItemDetailViewModel(application, productId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}