package com.example.lifesharing.mypage.work

import android.util.Log
import com.example.lifesharing.login.model.response_body.NickNameChangeResponse
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class NicknameChangeWork {

    // Retrofit을 사용하여 HTTP 요청 수행
    private var service = RetrofitAPIwithToken.retrofit()
    val TAG : String = "닉네임 변경 로그"

    fun changeNickname(nickname : String, callback: (Boolean) -> Unit){
        service.nicknameChange(nickname).enqueue(object : retrofit2.Callback<NickNameChangeResponse>{
            // 요청 성공 시 호출되는 콜백 메서드
            override fun onResponse(
                call: Call<NickNameChangeResponse>,
                response: Response<NickNameChangeResponse>
            ) {
                // 응답이 성공적(true)일 경우
                if (response.isSuccessful){
                    callback(true)   // 닉네임 변경 성공 시 콜백 함수를 이용해 true 반환
                    //nicknameChangeSuccess.postValue(true)   // 변경 성공 결과를 LiveData에 설정
                    Log.d(TAG, "닉네임 변경 성공 : ${response.body()}")
                }
                // 응답이 실패(false)일 경우
                else{
                    callback(false)   // 닉네임 변경 실패 시 콜백 함수를 이용해 false 반환
                    //nicknameChangeSuccess.postValue(false)  // 변경 실패 결과를 LiveData에 설정
                    Log.e(TAG, "닉네임 변경 실패 : ${response}")
                }
            }

            // 요청 실패 시 호출되는 콜백 메서드
            override fun onFailure(call: Call<NickNameChangeResponse>, t: Throwable) {
                callback(false)   // 닉네임 변경 실패 시 콜백 함수를 이용해 false 반환
                //nicknameChangeSuccess.postValue(false)
                Log.e(TAG, "네트워크 오류 : ${t.message}")
            }

        })
    }
}