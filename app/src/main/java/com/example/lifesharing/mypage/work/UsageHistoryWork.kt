package com.example.lifesharing.mypage.work

import android.util.Log
import com.example.lifesharing.mypage.model.response_body.UsageHistoryResponse
import com.example.lifesharing.mypage.model.response_body.UsageHistoryResult
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class UsageHistoryWork {
    private val service = RetrofitAPIwithToken.retrofit()
    val TAG : String = "이용 내역 로그"

    fun getUsageHistory(callback: (List<UsageHistoryResult>?, String?) -> Unit) {
        service.getUserUseHistory().enqueue(object : retrofit2.Callback<UsageHistoryResponse>{
            override fun onResponse(
                call: Call<UsageHistoryResponse>,
                response: Response<UsageHistoryResponse>
            ) {
                if (response.isSuccessful){
                    val result = response.body()
                    callback(result?.result, null)

                    Log.d(TAG, "$result")
                }
                Log.e(TAG, "조회 실패 : $response")
            }

            override fun onFailure(call: Call<UsageHistoryResponse>, t: Throwable) {
                Log.e(TAG, "네트워크 통신 오류 : ${t.message}")
            }

        })
    }
}