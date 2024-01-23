package com.example.lifesharing.service.work

import android.util.Log
import com.example.lifesharing.model.request_body.RegisterRequestBody
import com.example.lifesharing.model.response_body.RegisterResponseBody
import com.example.lifesharing.service.api.RetrofitAPI
import com.example.lifesharing.service.api.RetrofitAPIwithToken
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
                    val result = response.body()

                    if (response.isSuccessful) {
                        Log.d(TAG, "RetrofitWork - onResponse() called 회원가입 성공 $result")
                        // 여기서 성공했을 시 accessToken을 저장하는 로직을 구현해야함 실제로는 로그인이 되었을 때 넣어지게끔 로직 구현
                    } else {
                        Log.d(TAG, "회원가입 실패 $result")
                    }
                }

                override fun onFailure(call: Call<RegisterResponseBody>, t: Throwable) {
                    Log.d(TAG, "RetrofitWork - onFailure() 화원가입 실패")
                }
            } )
    }
}