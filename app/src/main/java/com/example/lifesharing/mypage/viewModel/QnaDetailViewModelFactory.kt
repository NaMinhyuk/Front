package com.example.lifesharing.mypage.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/** ViewModelProvider.Factory 인터페이스를 구현하여, 특정 파라미터를 포함한 ViewModel 인스턴스를 생성할 수 있는 사용자 정의 팩토리
 * inquiryId 라는 특정 문의 ID가 필요, 이 ID는 QnaDetailViewModel 내부에서 해당 문의의 상세 정보를 로드하는 데 사용
 */
class QnaDetailViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QnaDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QnaDetailViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
