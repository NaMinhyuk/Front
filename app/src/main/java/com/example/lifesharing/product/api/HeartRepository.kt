package com.example.lifesharing.product.api


import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call

class HeartRepository() {
    private val apiService = RetrofitAPIwithToken.retrofit()
    fun addHeart(productId: Long): Call<AddHeartResponse> {
        return apiService.addHeart(productId)
    }

    fun deleteHeart(productId: Long): Call<DeleteHeartResponse> {
        return apiService.delHeart(productId)
    }
}