package com.example.lifesharing.mypage.work

import android.util.Log
import com.example.lifesharing.mypage.model.response_body.MyRegProductList
import com.example.lifesharing.mypage.model.response_body.RegisterHistoryResponse
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

/** 등록내역 api 요청 Model */
class RegistListWork {
    // Retrofit을 사용하여 HTTP 요청 수행
    private val service = RetrofitAPIwithToken.retrofit()
    val TAG : String = "마이페이지 등록 내역 로그"

    fun getRegisterList(callBack: (List<MyRegProductList>?, Int?) -> Unit){
        service.getRegisterHistory().enqueue(object : retrofit2.Callback<RegisterHistoryResponse>{
            // 요청 성공 시 호출되는 콜백 메서드
            override fun onResponse(
                call: Call<RegisterHistoryResponse>,
                response: Response<RegisterHistoryResponse>
            ) {
                val result = response.body()?.result   // 응답 Body를 result객체에 담음

                callBack(result?.myRegProductList, result?.productCount)   // 응답 결과 리스트와 제품 개수 콜백함수로 전달

                Log.d(TAG, "register List : $result ")
                Log.d(TAG, "Product Count : ${result?.productCount}")
            }

            // 요청 실패 시 호출되는 콜백 메서드
            override fun onFailure(call: Call<RegisterHistoryResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}", )
                callBack(emptyList(), 0)
            }

        })
    }
}