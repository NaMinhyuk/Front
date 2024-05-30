package com.example.lifesharing.mypage.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.mypage.work.QnaWriteWork
import okhttp3.MultipartBody

/** 문의 작성 요청 ViewModel
 * AndroidViewModel을 상속받아 애플리케이션 컨텍스트를 사용
 */
class QnaWriteViewModel(application: Application) : AndroidViewModel(application) {
    private val qnaWriteWork = QnaWriteWork()
    val resultLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun writeQna(inquiryDTO: MultipartBody.Part, imageList: ArrayList<MultipartBody.Part>) {
        qnaWriteWork.writeQna(inquiryDTO, imageList) { isSuccess, message ->
            resultLiveData.postValue(isSuccess)
        }
    }
}