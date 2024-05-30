package com.example.lifesharing.product.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lifesharing.product.api.AddHeartResponse
import com.example.lifesharing.product.api.DeleteHeartResponse
import com.example.lifesharing.product.api.HeartRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeartViewModel(private val repository: HeartRepository) : ViewModel() {
    private val _addHeartResponse = MutableLiveData<AddHeartResponse?>()
    val addHeartResponse: LiveData<AddHeartResponse?> get() = _addHeartResponse

    private val _deleteHeartResponse = MutableLiveData<DeleteHeartResponse?>()
    val deleteHeartResponse: LiveData<DeleteHeartResponse?> get() = _deleteHeartResponse

    fun addHeart(productId: Long) {
        repository.addHeart(productId).enqueue(object : Callback<AddHeartResponse> {
            override fun onResponse(call: Call<AddHeartResponse>, response: Response<AddHeartResponse>) {
                if (response.isSuccessful) {
                    _addHeartResponse.value = response.body()
                    Log.d(TAG, "찜 추가 성공: ${response.body()}")

                    // 이미 좋아요한 상품일 때의 처리 로직 추가
                    if (response.body()?.code == "HEART4001") {
                        // 찜 삭제 요청
                        deleteHeart(productId)
                        Log.d(TAG, "이미 찜된 상태, 찜 삭제 요청 발송")
                    }
                }
                else {
                    val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                    _addHeartResponse.value = null
                    Log.e(TAG, "찜 추가 실패: $errorMsg")
                }
            }

            override fun onFailure(call: Call<AddHeartResponse>, t: Throwable) {
                _addHeartResponse.value = null
                Log.e(TAG, "찜 추가 오류", t)
            }
        })
    }

    fun deleteHeart(productId: Long) {
        repository.deleteHeart(productId).enqueue(object : Callback<DeleteHeartResponse> {
            override fun onResponse(call: Call<DeleteHeartResponse>, response: Response<DeleteHeartResponse>) {
                if (response.isSuccessful) {
                    _deleteHeartResponse.value = response.body()
                    Log.d(TAG, "찜 삭제 성공: ${response.body()}")

                    // 이미 좋아요한 상품일 때의 처리 로직 추가
                    if (response.body()?.code == "HEART4002") {
                        // 찜 삭제 요청
                        addHeart(productId)
                        Log.d(TAG, "이미 삭제된 상태, 찜 요청 발송")
                    }
                }
                else {
                    val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                    _deleteHeartResponse.value = null
                    Log.e(TAG, "찜 삭제 실패: $response")
                }
            }

            override fun onFailure(call: Call<DeleteHeartResponse>, t: Throwable) {
                _deleteHeartResponse.value = null
                Log.e(TAG, "찜 삭제 오류", t)
            }
        })
    }

    companion object {
        private const val TAG = "하트뷰모델"
    }
}