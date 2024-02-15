package com.example.lifesharing.service.work

import android.util.Log
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.mypage.review.model.response_body.GetReviewResponseBody
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class GetReviewListWork {

    private val service = RetrofitAPIwithToken.retrofit()

    val TAG: String = "로그"

    fun getReviewList() {
        service.getReviewList()
            .enqueue(object : retrofit2.Callback<GetReviewResponseBody> {
                override fun onResponse(
                    call: Call<GetReviewResponseBody>,
                    response: Response<GetReviewResponseBody>
                ) {
                    val result = response.body()
                    Log.d(TAG, "review List : $result")

                    Log.d(TAG, "나의 리뷰 갯수 : ${result!!.result.reviewCount}")
                    GlobalApplication.setMyReviewCount(result.result.reviewCount!!)

                    Log.d(TAG, "리뷰 데이터 : ${result.result.reviewListDTOList}")
                    GlobalApplication.setMyReviewData(result.result.reviewListDTOList)
                }

                override fun onFailure(call: Call<GetReviewResponseBody>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}", )
                }

            })
    }
}