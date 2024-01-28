package com.example.lifesharing.home

import android.util.Log
import com.example.lifesharing.home.home_data.ProductResponse
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class ProductFilter(private val filter: String) {

    val TAG = "로그"

    private val service = RetrofitAPIwithToken.retrofit()



}