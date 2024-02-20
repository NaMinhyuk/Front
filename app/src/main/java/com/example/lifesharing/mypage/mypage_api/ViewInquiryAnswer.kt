package com.example.lifesharing.mypage.mypage_api

import android.util.Log
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class ViewInquiryAnswer() {

    private val TAG: String = "로그"

    private val service = RetrofitAPIwithToken.retrofit()

    val inquiryId : Long = 0

    fun getViewInquiryAnswer(callback: (result: ViewInquiryAnswerResponse?) -> Unit) {
        service.getViewInquiryAnswer(inquiryId).enqueue(object : retrofit2.Callback<ViewInquiryAnswerResponse> {
            override fun onResponse(
                call: Call<ViewInquiryAnswerResponse>,
                response: Response<ViewInquiryAnswerResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()

                    Log.d(TAG, "qna 질문 데이터: ${result!!.result.inquiry}")
                    Log.d(TAG, "qna 답변 데이터 : ${result!!.result.reply}")

                } else { }
            }

            override fun onFailure(call: Call<ViewInquiryAnswerResponse>, t: Throwable) {
                Log.d(TAG, "데이터 받아오기 실패: ${t.message}")
                callback.invoke(null)
            }
        })
    }
}