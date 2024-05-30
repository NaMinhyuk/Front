package com.example.lifesharing.mypage.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.mypage.model.response_body.QnaDetailResponse
import com.example.lifesharing.mypage.work.QnaDetailWork

/** 문의 정보 상세 조회 ViewModel
 * AndroidViewModel을 상속받아 애플리케이션 컨텍스트를 사용
 */
class QnaDetailViewModel(application: Application): AndroidViewModel(application) {
    private val qnaDetailWork = QnaDetailWork()   // Model 과 통신을 위한 객체
    val qnaDetail : MutableLiveData<QnaDetailResponse> = MutableLiveData()   // 상세 정보 관찰 LiveDAta
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()   // 제품 정보 로딩 중 관찰 LiveData
    val error: MutableLiveData<String> = MutableLiveData()   // 에러 발생시 사용

    fun getQnaDetail(inquiryId : Long) {
        isLoading.value = true
        qnaDetailWork.getQnaDetail(inquiryId) { response, err ->
            isLoading.value = false
            response?.let {
                qnaDetail.value = it
            } ?: run {
                error.value = err ?: "Unknown error"
            }
        }
    }
}