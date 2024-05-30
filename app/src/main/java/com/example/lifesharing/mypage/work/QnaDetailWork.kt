package com.example.lifesharing.mypage.work

import android.util.Log
import com.example.lifesharing.mypage.model.response_body.QnaDetailResponse
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** 문의 정보 상세 조회 api Model */
class QnaDetailWork {
    // Retrofit을 사용하여 HTTP 요청 수행
    private val service = RetrofitAPIwithToken.retrofit()

    val TAG: String = "문의 사항 상세 로그"

    fun getQnaDetail(inquiryId: Long, callback: (QnaDetailResponse?, String?) -> Unit){
        Log.d(TAG, "get InquiryId: $inquiryId")   // 넘겨 받은 문의 ID 확인용 로그

        service.getInquiryDetail(inquiryId).enqueue(object : Callback<QnaDetailResponse> {
            // 요청 성공 시 호출되는 콜백 메서드
            override fun onResponse(
                call: Call<QnaDetailResponse>,
                response: Response<QnaDetailResponse>
            ) {
                // 응답이 성공적(true)일 경우
                if (response.isSuccessful){
                    callback(response.body(), null)
                    Log.d(TAG, "문의 상세 정보 가져오기 성공 :  ${response.body()}")
                }
                // 응답이 실패(false)일 경우
                else {
                    callback(null, "문의 상세 정보 가져오기 실패")
                    Log.e(TAG, "문의 상세 정보 가져오기 실패: ${response}")
                }
            }

            // 요청 실패 시 호출되는 콜백 메서드
            override fun onFailure(call: Call<QnaDetailResponse>, t: Throwable) {
                callback(null, t.message)
                Log.e(TAG, "HTTP error 발생, 상태 코드 : ${t.cause}, 에러 메시지: ${t.message}" )
            }

        })
    }
}