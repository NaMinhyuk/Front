package com.example.lifesharing.mypage.mypage_api

import android.util.Log
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Multipart

class RegisterQnA(private val qnaData: InquiryRegisterRequestBody, private val imageList : ArrayList<MultipartBody.Part>) {


    private val TAG: String = "로그"

    private val service = RetrofitAPIwithToken.retrofit()

    fun registerQna() {
        service.registerQna(qnaData, imageList)
            .enqueue(object : retrofit2.Callback<InquiryRegisterResponseBody> {
                override fun onResponse(
                    call: Call<InquiryRegisterResponseBody>,
                    response: Response<InquiryRegisterResponseBody>
                ) {
                    val result = response.body()
                    Log.d(TAG, "qna 등록 정보: $result")
                }
                override fun onFailure(call: Call<InquiryRegisterResponseBody>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }

            })
    }

}