package com.example.lifesharing.product.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.product.api.ProductReview
import com.example.lifesharing.product.work.ProductReviewWork

/** 제품 리뷰 조회 ViewModel */
class ProductReviewViewModel(application: Application): AndroidViewModel(application) {
    private val productReviewWork = ProductReviewWork()

    val reviews = MutableLiveData<List<ProductReview>>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String?>()

    fun loadProductReviews(productId: Long) {
        isLoading.postValue(true)
        productReviewWork.getProductReview(productId) { response, msg ->
            isLoading.postValue(false)
            if (response != null) {
                // 데이터 설정
                reviews.postValue(response?.result?.productReviewDTOList)
                errorMessage.postValue(msg)
            } else {
                // 오류 처리
                errorMessage.postValue(msg)
                Log.e("ProductReviewViewModel", "리뷰목록 로딩 오류: $msg")
            }
        }
    }
}