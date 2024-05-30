package com.example.lifesharing.mypage.work

import android.util.Log
import com.example.lifesharing.mypage.model.response_body.ChangePwResponse
import com.example.lifesharing.mypage.mypage_data.PwData
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

/** 비밀번호 변경 요청 api Model*/
class PwChangeWork {
    // Retrofit을 사용하여 HTTP 요청 수행
    val service = RetrofitAPIwithToken.retrofit()
    val TAG : String = "비밀번호 변경 상태 로그"

    fun changePw(changePw : PwData, callback: (ChangePwResponse?) -> Unit){
        service.changePw(changePw).enqueue(object : retrofit2.Callback<ChangePwResponse>{
            // 요청 성공 시 호출되는 콜백 메서드
            override fun onResponse(
                call: Call<ChangePwResponse>,
                response: Response<ChangePwResponse>
            ) {
                // 응답이 성공적(true)일 경우
                if (response.isSuccessful){
                    val result = response.body()?.result
                    //changePwData.postValue(response.body())     // 변경 결과 body를 LiveData에 저장 -> 응답 코드 & 응답 결과에 따라 UI 업데이트
                    callback(response.body())    // 변경 결과 body를 콜백 함수에 전달
                    Log.d(TAG, "new password status:  ${result?.isChanged}")  // 정상적으로 변경되었는지 결과 확인용 로그
                }
                // 응답이 실패(false)일 경우
                else {
                    //changePwData.postValue(ChangePwResponse(false, "USER_400_2", "기존 비밀번호가 잘못되었습니다.", null))
                    callback(ChangePwResponse(false, "USER_400_2", "기존 비밀번호가 잘못되었습니다.", null))
                    Log.d(TAG, "new password status:  ${response}")
                }
            }

            // 요청 실패 시 호출되는 콜백 메서드
            override fun onFailure(call: Call<ChangePwResponse>, t: Throwable) {
                Log.e("통신 실패", "${t.message}")
                callback(null)
            }

        })
    }
}