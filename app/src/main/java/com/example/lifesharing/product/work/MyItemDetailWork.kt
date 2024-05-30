package com.example.lifesharing.product.work

import android.util.Log
import com.example.lifesharing.common.response_body.ProductIdResponseBody
import com.example.lifesharing.product.data.MyItemDetailData
import com.example.lifesharing.product.data.MyItemDetailResultDTO
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class MyItemDetailWork {

    private val TAG: String = "로그"
    private val service = RetrofitAPIwithToken.retrofit()

    fun getProductDetails(productId: Long, callback: (MyItemDetailData?, String?) -> Unit) {
        service.getProductData(productId).enqueue(object : retrofit2.Callback<ProductIdResponseBody>{
            override fun onResponse(
                call: Call<ProductIdResponseBody>,
                response: Response<ProductIdResponseBody>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    Log.d(TAG, "제품에 대한 정보 조회 성공 : ${response.body()}")
                    val detailData = responseBody.result?.let { product ->
                        MyItemDetailData(
                            isSuccess = responseBody.isSuccess ?: false,
                            code = responseBody.code ?: "Unknown code",
                            message = responseBody.message ?: "No message provided",
                            myProductResultDTO = MyItemDetailResultDTO(
                                productId = product.productId!!,
                                userId = product.userId!!,
                                categoryList = product.categoryId!!,
                                location = product.location!!,
                                detailLocation = product.detailAddress!!,
                                name = product.name!!,
                                imageUrl = product.imageUrl,
                                score = product.score!!,
                                reviewCount = product.reviewCount!!,
                                deposit = product.deposit!!,
                                dayPrice = product.dayPrice!!,
                                hourPrice = product.hourPrice!!,
                                isLiked = product.isLiked!!,
                                content = product.content!!,
                                nickname = product.userNickname!!,
                                userImage = product.userImage
                            )
                        )
                    }
                    // 올바르게 생성된 MyItemDetailData를 콜백을 통해 반환
                    if (detailData != null) {
                        callback(detailData, null)
                        //Log.d(TAG, "제품에 대한 정보 조회 성공 : ${response.body()}")
                    } else {
                        callback(null, "결과를 변환하는 과정에서 문제가 발생했습니다.")
                        Log.e(TAG, "제품에 대한 정보 조회 실패 : ${response}")
                    }
                } else {
                    callback(null, "Error ${response.code()}: ${response.message()}")
                    Log.e(TAG, "서버에서 유효한 데이터를 받지 못했습니다. 결과가 null입니다.")
                }
            }

            override fun onFailure(call: Call<ProductIdResponseBody>, t: Throwable) {
                callback(null, t.message ?: "Unknown error")
                Log.e(TAG, "서버에서 유효한 데이터를 받지 못했습니다. 결과가 null입니다.")
            }

        })

    }
}