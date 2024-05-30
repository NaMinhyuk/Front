package com.example.lifesharing.mypage.work

import android.util.Log
import com.example.lifesharing.mypage.model.response_body.InquiryItem
import com.example.lifesharing.mypage.model.response_body.QnaWaitResponse
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** 문의 내역 조회 요청 Model */
class QnaWaitListWork {
    // Retrofit을 사용하여 HTTP 요청 수행
    private val service = RetrofitAPIwithToken.retrofit()

    val TAG : String = "문의 목록 조회 로그"

    // 문의 내역 조회
    fun getQnaList(lastInquiryId: Long? = 9223372036854775807, size: Int = 10, callback: (List<InquiryItem>?, Throwable?) -> Unit){
        service.getInquiryList(lastInquiryId, size).enqueue(object : Callback<QnaWaitResponse> {
            // 요청 성공 시 호출되는 콜백 메서드
            override fun onResponse(
                call: Call<QnaWaitResponse>,
                response: Response<QnaWaitResponse>
            ) {
                // 응답이 성공적(true)일 경우
                if (response.isSuccessful) {
                    val result = response.body()?.result
                    //qnaListItem.postValue(result?.inquiryList ?: emptyList())
                    callback(response.body()?.result?.inquiryList, null)
                    Log.d(TAG, "문의 목록 조회 성공 : ${result}")
                }
                // 응답이 실패(false)일 경우
                else {
                    Log.e(TAG, "Error loading inquiries: ${response.errorBody()?.string()}")
                    callback(null, Exception("문의 목록 조회 실페"))
                }
            }

            // 요청 성공 시 호출되는 콜백 메서드
            override fun onFailure(call: Call<QnaWaitResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}", )
                callback(null, t)
            }

        })
    }

}