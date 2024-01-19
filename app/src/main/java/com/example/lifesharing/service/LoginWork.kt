package com.example.lifesharing.service

import android.util.Log
import com.example.lifesharing.model.request_body.LoginRequestBody
import com.example.lifesharing.model.request_body.RegisterRequestBody
import com.example.lifesharing.model.response_body.LoginResponseBody
import com.example.lifesharing.model.response_body.RegisterResponseBody
import retrofit2.Call
import retrofit2.Response

class LoginWork(private val userInfo: LoginRequestBody) {

    val TAG: String = "로그"

    private val service = RetrofitAPI.emgMedService

    fun registerWork() {

        // call 작업은 두가지로 수행
        // execute - request 보내고 response를 받는 행위를 동기적!
        // enqueue - request 비동기적, response 콜백, 즉 동기적!
        service.loginUserByEnqueue(userInfo)
            .enqueue(object : retrofit2.Callback<LoginResponseBody> {
                override fun onResponse(
                    call: Call<LoginResponseBody>,
                    response: Response<LoginResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d(TAG, "RetrofitWork - onResponse() called 로그인 성공 $result")
                    }
                }

                override fun onFailure(call: Call<LoginResponseBody>, t: Throwable) {
                    Log.d(TAG, "RetrofitWork - onFailure() 화원가입 실패")
                }

            } )
    }
}