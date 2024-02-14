package com.example.lifesharing.service.work

import android.util.Log
import com.example.lifesharing.mypage.review.model.request_body.ReviewRequestBody
import com.example.lifesharing.mypage.review.model.response_body.ReviewResponseBody
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

class RegisterReviewWork(private val reservationId : Int, private val reviewData : ReviewRequestBody, private val imageList: ArrayList<MultipartBody.Part>) {
    private val retrofitService = RetrofitAPIwithToken.retrofit()

    val TAG: String = "로그"

    fun registerReview() {
        retrofitService.registerReview(reservationId, reviewData, imageList)
            .enqueue(object : retrofit2.Callback<ReviewResponseBody> {
                override fun onResponse(
                    call: Call<ReviewResponseBody>,
                    response: Response<ReviewResponseBody>
                ) {
                    val result = response.body()
                    Log.d(TAG, "onResponse: $result")
                    Log.d(TAG, "ArrayList:  $imageList")
                }

                override fun onFailure(call: Call<ReviewResponseBody>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }

            })
    }

}