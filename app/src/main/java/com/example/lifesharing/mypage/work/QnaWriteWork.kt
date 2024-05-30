package com.example.lifesharing.mypage.work

import android.util.Log
import com.example.lifesharing.mypage.model.response_body.QnaResponse
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

/** QnA 작성 요청 Api Model */
class QnaWriteWork {

    // Retrofit을 사용하여 HTTP 요청 수행
    private val retrofitService = RetrofitAPIwithToken.retrofit()

    val TAG : String = "로그"

    fun writeQna(inquiryDTO : MultipartBody.Part, imageList: ArrayList<MultipartBody.Part>, callback: (Boolean, String) -> Unit) {
        retrofitService.registerQna(inquiryDTO, imageList).enqueue(object : retrofit2.Callback<QnaResponse>{
            // 요청 성공 시 호출되는 콜백 메서드
            override fun onResponse(call: Call<QnaResponse>, response: Response<QnaResponse>) {
                // 응답이 성공적(true)일 경우
                if (response.isSuccessful){
                    val result = response.body()
                    callback(true, "QnA 작성 성공")
                    Log.d(TAG, "onResponse: $result")   // 요청 성공 확인용 로그
                }
                // 응답이 실패(false)일 경우
                else {
                    callback(false, "QnA 작성 실패: ${response.errorBody()?.string() ?: "알 수 없는 에러"}")
                    // 서버로부터 에러 응답을 받음 (예: 4xx or 5xx HTTP Status)
                    Log.d(TAG, "onResponse - not successful: ${response.errorBody()?.string()}")
                    Log.d(TAG, "HTTP Status Code: ${response.code()}")
                }
                Log.d(TAG, "ArrayList: $imageList")
            }

            // 요청 실패 시 호출되는 콜백 메서드
            override fun onFailure(call: Call<QnaResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                callback(false, "네트워크 통신 오류: ${t.message}")
            }

        })
    }
}