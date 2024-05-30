package com.example.lifesharing.mypage.work

import android.util.Log
import com.example.lifesharing.mypage.model.response_body.UserProfileInfoResponse
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

/** 마이페이지 진입 시 요청 Model */
class MyPageInfoWork {
    // Retrofit을 사용하여 HTTP 요청 수행
    private val service = RetrofitAPIwithToken.retrofit()

    val TAG : String = "내 정보 요청 로그"

    fun getUserProfileInfo(callback: (UserProfileInfoResponse?, String?) -> Unit){
        service.getUserInfo().enqueue(object : retrofit2.Callback<UserProfileInfoResponse>{
            // 요청 성공 시 호출되는 콜백 메서드
            override fun onResponse(
                call: Call<UserProfileInfoResponse>,
                response: Response<UserProfileInfoResponse>
            ) {
                // 응답이 성공적일 경우
                if(response.isSuccessful){
                    val result = response.body()?.result
                    //userInfo.postValue(response.body()?.result)  // 응답 Body에서 사용자 정보를 LiveData에 설절
                    callback(response.body(), null)

                    Log.d(TAG, "userId: ${result?.userId}")  // 올바른 정보가 들어왔는지 확인용 로그
                    Log.d(TAG, "onResponse: $result")
                }
                // 응답이 실패일 경우
                else{
                    callback(null, "사용자 정보 불러오기 실패: ${response.errorBody()?.string()}")
                    Log.e(TAG, "사용자 정보 불러오기 실패: ${response.errorBody()}")
                }
            }

            // 요청 실패 시 호출되는 콜백 메서드
            override fun onFailure(call: Call<UserProfileInfoResponse>, t: Throwable) {
                callback(null, "네트워크 오류 통신 실패: ${t.message}")
                Log.e(TAG, "네트워크 오류 통신 실패: ${t.message}")
            }

        })
    }
}