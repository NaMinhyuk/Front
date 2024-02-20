package com.example.lifesharing.service.work

import com.example.lifesharing.product.model.request_body.ProductPayRequestBody
import com.example.lifesharing.service.api.RetrofitAPI
import com.example.lifesharing.service.api.RetrofitAPIwithToken

class PayProduct(private val productId: Int, requestBody : ProductPayRequestBody) {
    val TAG: String = "pay"

//    private val service = RetrofitAPIwithToken.retrofit()
//    private val service1 = retrofit.create(ApiService::class.java)
//    fun payProductAPI() {
//
//    }
}