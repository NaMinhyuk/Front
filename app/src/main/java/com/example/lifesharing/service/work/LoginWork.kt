package com.example.lifesharing.service.work

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.model.request_body.LoginRequestBody
import com.example.lifesharing.service.api.RetrofitAPI
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginWork(private val userInfo: LoginRequestBody) {

    val TAG: String = "login"

    private val service = RetrofitAPI.emgMedService

    private val service1 = RetrofitAPIwithToken.retrofit()

    fun loginWorkWithCoroutine() {

        CoroutineScope(Dispatchers.IO).launch {
            // POST request를 보내고 reponse를 받음
            try {
                val response = service.loginUser(userInfo)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d("로그인 성공", "$result")

                        val successResult = result?.result?.token
                        GlobalApplication.prefs.setString("access_token", successResult!! )
                        Log.d(TAG, "로그인 액세스 토큰 ${GlobalApplication.prefs.getString("access_token", "")}")
                    } else {
                        Log.d("로그인 실패", response.code().toString())
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "로그인 에러 ${e.message}")
            }
        }



    }
}

