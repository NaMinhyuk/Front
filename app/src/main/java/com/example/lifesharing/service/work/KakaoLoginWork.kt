package com.example.lifesharing.service.work

import android.util.Log
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.mypage.work.MyPageUserInfo
import com.example.lifesharing.service.api.RetrofitAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KakaoLoginWork(val idToken: String) {

    val TAG: String = "카카오 로그인 로그"

    private val service = RetrofitAPI.emgMedService

    // 카카오 로그인 작업을 코루틴을 사용하여 비동기적으로 처리하는 함수를 정의
    fun kakaoLoginWorkCoroutine() {

        // I/O 작업에 최적화된 코루틴 스코프를 생성하고 시작
        CoroutineScope(Dispatchers.IO).launch {
            // POST request를 보내고 reponse를 받음
            try {
                // 카카오 유저의 정보를 가져오도록 네트워크 요청 수행
                val response = service.getKakaoUser(idToken)
                withContext(Dispatchers.Main) {  // 요청 결과를 메인 스레드에서 처리하기 위함

                    if (response.isSuccessful) {      // 응답이 성공적일 경우
                        val result = response.body()
                        Log.d("로그인 성공", "$result")

                        // 응답으로부터 accessToken, refreshToken, userId 추출우
                        val accessToken = result?.result?.tokenDTO?.accessToken
                        val refreshToken = result?.result?.tokenDTO?.refreshToken
                        val userId = result?.result?.userId

                        // 로컬 저장소에 저장
                        GlobalApplication.prefs.setString("user_id", userId.toString())
                        GlobalApplication.prefs.setString("access_token", accessToken!! )
                        GlobalApplication.prefs.setString("refresh_token", refreshToken!!)

                        // 사용자 정보 요청
                        MyPageUserInfo().getMyPageUserInfo()

                        Log.d(TAG, "유저 id ${GlobalApplication.prefs.getString("user_id", userId.toString())} ")
                        Log.d(TAG, "로그인 액세스 토큰 ${GlobalApplication.prefs.getString("access_token", "")}")
                    } else {
                        Log.d("로그인 실패", response.body().toString())
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "로그인 에러 ${e.message}")
            }
        }

    }


}