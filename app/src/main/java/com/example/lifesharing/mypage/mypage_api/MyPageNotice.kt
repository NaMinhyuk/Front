package com.example.lifesharing.mypage.mypage_api

import android.util.Log
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class MyPageNotice {

    private val service = RetrofitAPIwithToken.retrofit()

    private val TAG = "로그"

    fun getNoticeList(
        lastNoticeId: Long,
        onSuccess: (List<NoticeResult>) -> Unit,
        onFailure: (String) -> Unit) {
        service.getNoticeList(lastNoticeId).enqueue(object : retrofit2.Callback<NoticeResponse> {
            override fun onResponse(call: Call<NoticeResponse>, response: Response<NoticeResponse>) {
                val result = response.body()
                if (response.isSuccessful) {
                    Log.d(TAG, "공지 사항 목록 $result")

                    val noticeResultList = result?.noticeResultList?.noticeResult
                    Log.d(TAG, "$noticeResultList")

                    noticeResultList?.let {
                        onSuccess(it)
                    } ?: run {
                        onFailure("null")
                    }

                } else {
                    Log.e(TAG, "통신 실패: ${response.message()}")
                }
            }

        override fun onFailure(call: Call<NoticeResponse>, t: Throwable) {
            Log.e(TAG, "목록 가져오기 실패: ${t.message}", )
        }
    })}
}