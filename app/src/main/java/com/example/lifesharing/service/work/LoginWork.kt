package com.example.lifesharing.service.work

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.login.model.request_body.LoginRequestBody
import com.example.lifesharing.login.model.response_body.LoginResponseBody
import com.example.lifesharing.mypage.work.MyPageUserInfo
import com.example.lifesharing.service.api.RetrofitAPI
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginWork(private val userInfo: LoginRequestBody) {

    val TAG: String = "로그"

    private val service = RetrofitAPI.emgMedService
    var showMainActivity : MutableLiveData<Boolean> = MutableLiveData(false)

    // 로그인 작업을 코루틴을 사용하여 수행하는 함수
    fun loginWorkWithCoroutine(onResult: (LoginResponseBody) -> Unit) {

        CoroutineScope(Dispatchers.IO).launch {
            // POST request를 보내고 reponse를 받음
            try {
                val response = service.loginUser(userInfo)
                withContext(Dispatchers.Main ) {
                    // 응답이 성공적인 경우
                    if (response.isSuccessful && response.body()?.isSuccess == true) {
                        val result = response.body()
                        Log.d("로그인 성공", "$result")

                        onResult(response.body()!!)   // 결과를 onResult 콜백으로 전달

                        // 액세스 토큰과 리프레시 토큰을 로컬 저장소에 저장
                        val accessToken = result?.result?.tokenDTO?.accessToken
                        val refreshToken = result?.result?.tokenDTO?.refreshToken
                        val userId = result?.result?.userId

                        GlobalApplication.prefs.setString("user_id", userId.toString())
                        GlobalApplication.prefs.setString("access_token", accessToken!! )
                        GlobalApplication.prefs.setString("refresh_token", refreshToken!!)

                        // 마이 페이지 정보를 가져오는 작업을 수행
                        MyPageUserInfo().getMyPageUserInfo()

                        // 메인 액티비티로 이동
                        showMainActivity.postValue(true)

                        Log.d(TAG, "유저 id ${GlobalApplication.prefs.getString("user_id", userId.toString())} ")
                        Log.d(TAG, "로그인 액세스 토큰 ${GlobalApplication.prefs.getString("access_token", "")}")
                    }
                    // 응답이 실패인 경우
                    else {
                        Log.d("로그인 실패", response.code().toString())

                        val responseBody = response.errorBody()?.string()
                        val errorResponse = Gson().fromJson(responseBody, LoginResponseBody::class.java)
                        onResult(errorResponse ?: LoginResponseBody(isSuccess = false, message = "Unknown error"))
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "로그인 에러 ${e.message}")
                onResult(LoginResponseBody(isSuccess = false, message = "Network error: ${e.message}"))
            }
        }

    }
}

