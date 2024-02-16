package com.example.lifesharing.service.work

import android.util.Log
import com.example.lifesharing.product.model.response_body.ProductMenuResponseBody
import com.example.lifesharing.reservation.ReservationResponseBody
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class MenuProduct () {
    private val retrofitService = RetrofitAPIwithToken.retrofit()
    val TAG :String = "로그"

    fun menuProductAPI() {
        //카테고리 id 들어가야함
        retrofitService.getProductMenu(1).enqueue(object : retrofit2.Callback<ProductMenuResponseBody> {
            override fun onResponse(
                call: Call<ProductMenuResponseBody>,
                response: Response<ProductMenuResponseBody>
            ) {
                val result = response.body()
                if(response.isSuccessful) {
                    Log.d(TAG, "메뉴 API 연동 성공? $result")
                } else {
                    Log.e(TAG, "메뉴 통신실패 원인은 ${response.message()}", )
                }
            }

            override fun onFailure(call: Call<ProductMenuResponseBody>, t: Throwable) {
                Log.e(TAG, "서버에도 못감 원인은 ${t.message}", )
            }


        })
    }
}