package com.example.lifesharing.mypage.mypage_api

import android.util.Log
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class ViewWishList {

    private val TAG: String = "로그"

    private val service = RetrofitAPIwithToken.retrofit()

    fun getWishList(onSuccess: (List<HeartResult>) -> Unit, onFailure: (String) -> Unit) {
        service.getWishList().enqueue(object : retrofit2.Callback<ViewWishListResponse> {
            override fun onResponse(call: Call<ViewWishListResponse>, response: Response<ViewWishListResponse>) {
                val result = response.body() // 데이터를 직접 처리
                if (response.isSuccessful) {
                    Log.d(TAG, "찜 목록 정보 $result")

                    Log.d(TAG, " ${result?.heartResultList?.heartResult} ")

                    } else {
                        Log.e(TAG, " 통신 실패: ${response.message()}", )
                    }
                }

                override fun onFailure(call: Call<ViewWishListResponse>, t: Throwable) {
                    onFailure("목록받아오기 실패: ${t.message}")
                }
            }
        )
    }
}