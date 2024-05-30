package com.example.lifesharing.mypage.work

import android.util.Log
import com.example.lifesharing.login.model.response_body.NickNameCheckResponse
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

/** 닉네임 중복 확인 api Model*/
class NicknameCheckWork {

    // Retrofit을 사용하여 HTTP 요청 수행
    val service = RetrofitAPIwithToken.retrofit()

    val TAG : String = "로그"

    // 입력된 닉네임을 인자로 넘겨받음
    fun checkNickname(nickname: String, callback: (NicknameStatus) -> Unit){
        service.nicknameCheck(nickname).enqueue(object : retrofit2.Callback<NickNameCheckResponse>{
            // 요청 성공 시 호출되는 콜백 메서드
            override fun onResponse(
                call: Call<NickNameCheckResponse>,
                response: Response<NickNameCheckResponse>
            ) {
                // 응답이 성공적일 경우
                if (response.isSuccessful)
                {
                    // 응답 Body에 담긴 existNickname(닉네임 존재 여부(T/F))를 저장
                    val result = response.body()?.result?.existNickname ?: false

                    // 존재 여부에 따라 콜백 함수에 결과를 설정
                    callback(if (result == true) NicknameStatus.Unavailable else NicknameStatus.Available)

                    Log.d(TAG, "넘겨받은 닉네임: $nickname")
                    Log.d(TAG, "존재하는 닉네임인가요 ? $result")
                }
                // 응답이 실패일 경우
                else {
                    callback(NicknameStatus.Error)
                    Log.d(TAG, "닉네임을 확인하지 못했습니다. 다시 시도해주세요. ${response.body()}")
                }
            }

            // 요청 실패 시 호출되는 콜백 메서드
            override fun onFailure(call: Call<NickNameCheckResponse>, t: Throwable) {
                Log.e("통신 실패", "${t.message}")
                callback(NicknameStatus.Error)
            }
        })
    }
}

enum class NicknameStatus {
    Available, Unavailable, Error
}