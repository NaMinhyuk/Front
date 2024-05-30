package com.example.lifesharing.product.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lifesharing.product.api.HeartRepository

class HeartViewModelFactory(private val repository: HeartRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HeartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HeartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
