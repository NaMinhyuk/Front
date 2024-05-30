package com.example.lifesharing.mypage.work

import android.util.Log
import com.example.lifesharing.mypage.model.response_body.HeartList
import com.example.lifesharing.mypage.model.response_body.HeartListResponse
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

/** 찜 목록 조회 요청 api Model*/
class HeartListWork {
    // Retrofit을 사용하여 HTTP 요청 수행
    private val service = RetrofitAPIwithToken.retrofit()
    val TAG: String = "로그"

    fun getHeartList(callback: (List<HeartList>?, Throwable?) -> Unit){
        service.getHeartList().enqueue(object : retrofit2.Callback<HeartListResponse>{
            // 요청 성공 시 호출되는 콜백 메서드
            override fun onResponse(
                call: Call<HeartListResponse>,
                response: Response<HeartListResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()?.result?.list ?: emptyList()
                    callback(response.body()?.result?.list, null)   // 콜백 함수에 응답 결과 리스트 전달
                    //heartListItem.postValue(result)     // LiveData에 응답 결과 리스트 설정
                    Log.d(TAG, "찜 목록 : $result")
                }
                else{
                    callback(null, RuntimeException("찜 목록 불러오기 실패 ,, "))
                    Log.e(TAG, "찜 목록 : ${response.body()}")
                }
            }

            // 요청 실패 시 호출되는 콜백 메서드
            override fun onFailure(call: Call<HeartListResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}", )
                callback(null, t)
            }

        })
    }
}