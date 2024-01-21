package com.example.lifesharing.service.work

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.lifesharing.model.request_body.LoginRequestBody
import com.example.lifesharing.model.response_body.LoginResponseBody
import com.example.lifesharing.model.response_body.LoginResult
import com.example.lifesharing.service.SharedData.SharedManager
import com.example.lifesharing.service.api.RetrofitAPI
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

import com.example.lifesharing.model.Result

class LoginWork(private val userInfo: LoginRequestBody) {

    val TAG: String = "login"

    private val service = RetrofitAPI.emgMedService


    fun loginWork() {
        // call 작업은 두가지로 수행
        // execute - request 보내고 response를 받는 행위를 동기적!
        // enqueue - request 비동기적, response 콜백, 즉 동기적!


        service.loginUserByEnqueue(userInfo)
            .enqueue(object : retrofit2.Callback<LoginResponseBody> {
                override fun onResponse(
                    call: Call<LoginResponseBody>,
                    response: Response<LoginResponseBody>
                ) {
                    val result = response.body()
                    9
                    if (response.isSuccessful) {
                        Log.d(TAG, "회원가입성공!!! $result")

                    } else {
                        Log.d(TAG, "회원가입실패 ㅠㅠ: $result")
                    }
                }

                override fun onFailure(call: Call<LoginResponseBody>, t: Throwable) {
                    Log.d(TAG, "RetrofitWork - onFailure() 화원가입 실패")
                }

            })
//        CoroutineScope(Dispatchers.IO).launch {
//            // POST request를 보내고 reponse를 받음
//            val response = service.loginUser(userInfo)
//            withContext(Dispatchers.Main) {
//                if (response.isSuccessful) {
//                    val result = response.body()
//                    Log.d(TAG, "로그인 성공: $result")
//                } else {
//                    Log.d(TAG, "로그인 실패: ${response.code().toString()} ")
//                }
//            }
//        }
    }

    suspend fun loginWorkWithCoroutine(): Result {

        val resultDeferred = CompletableDeferred<Result>()

        CoroutineScope(Dispatchers.IO).launch {
            // POST request를 보내고 reponse를 받음
            try {
                val response = service.loginUser(userInfo)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d("회원가입 성공", "$result")
                        resultDeferred.complete(Result.Success(result))
                    } else {
                        Log.d("회원가입 실패", response.code().toString())
                        resultDeferred.complete(Result.Error(response.code().toString()))
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "로그인 에러 ${e.message}")
                resultDeferred.complete(Result.Error(e.message ?: "Unknown error"))
            }
        }
        return resultDeferred.await()
    }
}

