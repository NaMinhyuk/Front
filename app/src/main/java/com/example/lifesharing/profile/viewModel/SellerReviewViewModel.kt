package com.example.lifesharing.profile.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.profile.model.response_body.ReviewData
import com.example.lifesharing.profile.work.SellerProfileWork
import com.example.lifesharing.profile.work.SellerReviewWork

class SellerReviewViewModel(application: Application): AndroidViewModel(application) {
    private val sellerReviewWork = SellerReviewWork()    // Model과 통신을 위한 객체
    val itemResult : MutableLiveData<List<ReviewData>?> = MutableLiveData()  // 응답 결과 제품 리스트를 위한 LiveData
    val errorMessage: MutableLiveData<String> = MutableLiveData()  // 에러 메시지를 위한 LiveData

    fun loadSellerReviews(sellerId: Long) {
        sellerReviewWork.getSellerReview(sellerId) { response, error ->
            if (error != null) {
                // 오류가 있을 경우, 오류 메시지를 LiveData에 게시
                errorMessage.postValue(error)
            } else if (response != null) {
                // 성공적으로 데이터를 받아온 경우, 결과를 LiveData에 게시
                itemResult.postValue(response.result?.reviewList)
            }
        }
    }

}