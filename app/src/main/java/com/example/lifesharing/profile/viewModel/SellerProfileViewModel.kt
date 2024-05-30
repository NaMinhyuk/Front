package com.example.lifesharing.profile.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.profile.model.response_body.SellerResponseBody
import com.example.lifesharing.profile.model.response_body.UserProfileResult
import com.example.lifesharing.profile.work.SellerProfileWork
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class SellerProfileViewModel(application: Application): AndroidViewModel(application) {
    private val sellerProfileWork = SellerProfileWork()
    // LiveData 객체 선언
    val sellerProfile: MutableLiveData<UserProfileResult?> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()  // 에러 메시지를 위한 LiveData

    fun loadSellerProfile(sellerId: Long) {
        sellerProfileWork.getSellerProfile(sellerId) { response, error ->   // 네트워크 응답을 비동기적으로 받아 처리
            if (error != null) {
                // 오류가 있을 경우, 오류 메시지를 LiveData에 게시
                errorMessage.postValue(error)
            } else if (response != null) {
                // 성공적으로 데이터를 받아온 경우, 결과를 LiveData에 게시
                sellerProfile.postValue(response.result)
            }
        }
    }
}