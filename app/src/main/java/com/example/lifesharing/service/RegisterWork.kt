package com.example.lifesharing.service

import android.util.Log
import com.example.lifesharing.model.request_body.RegisterRequestBody
import com.example.lifesharing.model.response_body.RegisterResponseBody
import retrofit2.Call
import retrofit2.Response

class RegisterWork(private val userInfo: RegisterRequestBody) {

    val TAG: String = "로그"

    private val service = RetrofitAPI.emgMedService

    fun registerWork() {

        // call 작업은 두가지로 수행
        // execute - request 보내고 response를 받는 행위를 동기적!
        // enqueue - request 비동기적, response 콜백, 즉 동기적!
        service.registerUserByEnqueue(userInfo)
            .enqueue(object : retrofit2.Callback<RegisterResponseBody> {
                override fun onResponse(
                    call: Call<RegisterResponseBody>,
                    response: Response<RegisterResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d(TAG, "RetrofitWork - onResponse() called 회원가입 성공 $result")
                    }
                }

                override fun onFailure(call: Call<RegisterResponseBody>, t: Throwable) {
                    Log.d(TAG, "RetrofitWork - onFailure() 화원가입 실패")
                }
            } )
    }
}