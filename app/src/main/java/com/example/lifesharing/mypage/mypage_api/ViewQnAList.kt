package com.example.lifesharing.mypage.mypage_api

import android.util.Log
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class ViewQnAList(){

    private val TAG: String = "로그"

    private val service = RetrofitAPIwithToken.retrofit()

    var size: Int = 8

    fun getInquiryList(){
        service.getInquiryList(size).enqueue(object : retrofit2.Callback<InquiryResponse> {
            override fun onResponse(
                call: Call<InquiryResponse>,
                response: Response<InquiryResponse>
            ) {
                var result = response.body()

                Log.d(TAG, "qna: $result")

                Log.d(TAG, "qna 리스트 데이터 : ${result!!.result.inquiryList}")

                GlobalApplication.setQnaListData(result.result.inquiryList)

            }

            override fun onFailure(call: Call<InquiryResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
}