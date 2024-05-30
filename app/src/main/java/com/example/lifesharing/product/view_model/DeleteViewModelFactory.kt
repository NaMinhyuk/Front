package com.example.lifesharing.product.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DeleteViewModelFactory(private val application: Application, private val productId: Long): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeleteViewModel::class.java)){
            return DeleteViewModel(application, productId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}