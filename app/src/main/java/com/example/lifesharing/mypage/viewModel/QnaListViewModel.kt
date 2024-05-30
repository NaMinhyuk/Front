package com.example.lifesharing.mypage.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.mypage.model.response_body.InquiryItem
import com.example.lifesharing.mypage.work.QnaWaitListWork

/** 문의 대기 내역 조회 ViewModel
 * AndroidViewModel을 상속받아 애플리케이션 컨텍스트를 사용
 */
class QnaListViewModel(application: Application) : AndroidViewModel(application){

    private val qnaWaitWork = QnaWaitListWork()  // Model과 통신의 위한 객체
    val qnaListItem : MutableLiveData<List<InquiryItem>> = MutableLiveData()   // 문의 목록 관찰 LiveDAta
    val TAG : String = "문의 목록 조회 로그"

    fun getQnaList(lastInquiryId: Long = Long.MAX_VALUE, size: Int = 10) {
        qnaWaitWork.getQnaList(lastInquiryId, size) { items, error ->
            if (error != null) {
                Log.e(TAG, "Error fetching QnA list", error)
                qnaListItem.postValue(emptyList())
            } else {
                qnaListItem.postValue(items ?: emptyList())
            }
        }
    }
}